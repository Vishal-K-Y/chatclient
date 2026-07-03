package com.coding.ai.chatclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RAGService {
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;
    private final ChatMemory chatMemory;

    @Value("classpath:general-concept.pdf")
    Resource pdfResource;

    public void ingestPDFInVectorStore() {
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(pdfResource);
        List<Document> documents = pagePdfDocumentReader.read();

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .withKeepSeparator(true)
                .withMinChunkSizeChars(50)
                .withMaxNumChunks(10000)
                .build();

        List<Document> chunks = splitter.apply(documents);

        vectorStore.add(chunks);
        log.info("pdf added in vector store");
    }

    public String chatWithCustomData(String prompt) {

        String template = """
            Instructions:
            - Base your answer SOLELY on the provided Context.
            - Strictly avoid introducing outside knowledge, external facts, or new concepts.
            - You may rephrase and summarize the information into a natural, cohesive explanation.
            - If multiple sections of the context are relevant, combine them into one response.
            - If the Context does not contain the answer, simply say: "Information not provided."
            
            Context:
            {context}
            
            Answer the user's request in a friendly, conversational tone.
        """;

        var docs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt)
                .similarityThreshold(0.5)
                .filterExpression("file_name == 'general-concept.pdf'") //it will filter non related content
                .topK(3)
                .build());

        var context = docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
        String stuffedPrompt = promptTemplate.render(Map.of("context", context));

        return chatClient.prompt()
                .system(stuffedPrompt)
                .user(prompt)
                .advisors()
                .call()
                .content();
    }
}
