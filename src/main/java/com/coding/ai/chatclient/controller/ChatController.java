package com.coding.ai.chatclient.controller;

import com.coding.ai.chatclient.service.AIChatService;
import com.coding.ai.chatclient.service.RAGService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatController {
    private AIChatService aiChatService;
    private RAGService ragService;

    @GetMapping("/")
    public String sayHi(){
        return "Hi, please talk to the model on '/chat' or search for your doubts on '/searchQuery'.";
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return aiChatService.chatWithAI(message);
    }

    @GetMapping("/searchQuery")
    public List<Document> findIfSimilarityExists(@RequestParam String query){
        return aiChatService.similaritySearch(query);
    }

    @PostMapping("/chatWithData")
    public String askCustomKnowledge(@RequestBody String message){
        return ragService.chatWithCustomData(message);
    }
}
