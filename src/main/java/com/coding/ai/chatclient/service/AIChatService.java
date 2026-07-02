package com.coding.ai.chatclient.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AIChatService {
    private final ChatClient chatClient;

    public String chatWithAI(String userInput){
        long startTime = System.currentTimeMillis();
        log.info("User input: {}", userInput);

        String aiResponse = chatClient.prompt()
                .user(userInput)
                .call()
                .content();

        long duration = System.currentTimeMillis() - startTime;
        log.info("AI responded in {}ms: {}", duration, aiResponse);

        return aiResponse;
    }
}
