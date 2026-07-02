package com.coding.ai.chatclient.controller;

import com.coding.ai.chatclient.service.AIChatService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {
    private AIChatService aiChatService;

    @GetMapping("/")
    public String sayHi(){
        return "Hi, please talk to the model on '/chat'";
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return aiChatService.chatWithAI(message);
    }
}
