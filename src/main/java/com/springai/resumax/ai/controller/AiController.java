package com.springai.resumax.ai.controller;

import com.springai.resumax.ai.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final AiService aiService;
    private final ChatClient chatClient;

    public AiController(AiService aiService, ChatClient client){
        this.aiService = aiService;
        this.chatClient = client;
    }

    //doing rag

    @GetMapping("/rag/{id}")
    public ResponseEntity<?> rag(@RequestParam(value = "q") String query, @PathVariable(value = "id")String userId){

        var response = aiService.rag(query,userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q") String query){

        return ResponseEntity.ok(chatClient.prompt(query).call().content());
    }

}
