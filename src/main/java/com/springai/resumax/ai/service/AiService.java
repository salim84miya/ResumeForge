package com.springai.resumax.ai.service;


import com.springai.resumax.ai.entity.UserResumeResponse;
import com.springai.resumax.profile.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {


    private final EmbeddingModel embeddingModel;
    private final ChatClient chatClient;
    private final VectorStore vectorStore;


    public void embedDocuments(){
        List<Document> documents = List.of(

                // ===== USER 1 (Backend Developer) =====
                new Document(
                        "User 1 Resume: Experienced backend developer skilled in Spring Boot, PostgreSQL, Redis, and Docker. Built scalable APIs and authentication systems.",
                        Map.of("userId", "user_1", "type", "resume")
                ),
                new Document(
                        "User 1 Project: Developed a society management system with JWT authentication, refresh tokens, and multi-device session handling.",
                        Map.of("userId", "user_1", "type", "project")
                ),
                new Document(
                        "User 1 Skills: Java, Spring Boot, Microservices, Redis caching, system design.",
                        Map.of("userId", "user_1", "type", "skills")
                ),

                // ===== USER 2 (Frontend Developer) =====
                new Document(
                        "User 2 Resume: Frontend developer specializing in React, Tailwind CSS, and modern UI/UX design. Focused on responsive and accessible web apps.",
                        Map.of("userId", "user_2", "type", "resume")
                ),
                new Document(
                        "User 2 Project: Built a fitness tracking web app with React, charts, and real-time state management.",
                        Map.of("userId", "user_2", "type", "project")
                ),
                new Document(
                        "User 2 Skills: JavaScript, React, CSS, UI/UX, performance optimization.",
                        Map.of("userId", "user_2", "type", "skills")
                )
        );
        // Add the documents to PGVector
        vectorStore.add(documents);

//        EmbeddingResponse response = embeddingModel.embedForResponse(List.of(query));
    }

    public UserResumeResponse rag(String query,String userId){

        UserResumeResponse response = chatClient.prompt()
                .advisors(QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(SearchRequest.builder()
                                .query(query)
                                .similarityThreshold(0.3)
                                .topK(3)
                                .filterExpression(new FilterExpressionBuilder().eq("userId",userId).build())
                                .build())
                        .build())
                .user(query)
                .call()
                .entity(ParameterizedTypeReference.forType(UserResumeResponse.class));


        return  response;
    }}
