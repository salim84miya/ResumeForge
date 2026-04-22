package com.springai.resumax.ai.controller;

import com.springai.resumax.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;


    //doing rag

    @GetMapping("/rag/{id}/{profileId}")
    public ResponseEntity<?> rag(@RequestParam(value = "q") String query,
                                 @PathVariable(value = "id")String userId,
                                 @PathVariable(value = "profileId")Long profileId){

        var response = aiService.rag(query,userId,profileId);

        return ResponseEntity.ok(response);
    }

}
