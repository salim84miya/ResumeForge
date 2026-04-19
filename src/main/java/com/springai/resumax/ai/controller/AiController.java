package com.springai.resumax.ai.controller;

import com.springai.resumax.ai.entity.UserResumeResponse;
import com.springai.resumax.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    //doing rag

    @GetMapping("/rag/{id}")
    public ResponseEntity<UserResumeResponse> rag(@RequestParam(value = "q") String query, @PathVariable(value = "id")String userId){

        var response = aiService.rag(query,userId);

        return ResponseEntity.ok(response);
    }

}
