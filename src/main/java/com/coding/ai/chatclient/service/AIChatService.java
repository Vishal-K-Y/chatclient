package com.coding.ai.chatclient.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AIChatService {
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public void ingestDataToVectorStore(String text){
        Document document=new Document(text);
        vectorStore.add(List.of(document));
    }

    public void ingestDataSetToVectorStore(List<Document> documents){
        vectorStore.add(documents);
        log.info("documents added in vector store.");
    }

    public List<Document> similaritySearch(String text){

        //return vectorStore.similaritySearch(text);
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(text)
                        .topK(2)        //no. of results
                        .similarityThreshold(0.5) //how close is the answer 0.0(least)- 1.0 (max)
                        .build());
    }

    public float[] convertTextToVector(String text) {
        // 1. Transform the text into a numerical 'vector'
        float[] vector = embeddingModel.embed(text);
        //vector Dimensions
        System.out.println("length-"+vector.length);

        for(float data:vector){
            System.out.print(" "+data);
        }
        return vector;
    }

    public String chatWithAI(String userInput) {
        long startTime = System.currentTimeMillis();
        log.info("User input: {}", userInput);

        // Get the full ChatResponse object
        var chatResponse = chatClient.prompt()
                .user(userInput)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .chatResponse();

        long duration = System.currentTimeMillis() - startTime;

        // Log the full object (includes token usage, model info, etc.)
        log.info("AI responded in {}ms: {}", duration, chatResponse);

        // Use .getText() instead of .getContent()
        if (chatResponse != null && chatResponse.getResult() != null) {
            return chatResponse.getResult().getOutput().getText();
        }

        return "No response";
    }
}
