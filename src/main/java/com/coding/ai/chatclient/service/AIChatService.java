package com.coding.ai.chatclient.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AIChatService {
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

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

    public String chatWithAI(String userInput){
        long startTime = System.currentTimeMillis();
        log.info("User input: {}", userInput);

        String aiResponse = chatClient.prompt()
                .user(userInput)
                .call()
                .content();

        long duration = System.currentTimeMillis() - startTime;
        log.info("AI responded in {}ms: {}", duration, aiResponse);

        //will remove this from here in the next push
        convertTextToVector(userInput);

        return aiResponse;
    }
}
