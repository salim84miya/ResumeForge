package com.springai.resumax.chat;

import com.springai.resumax.chat.entity.*;
import com.springai.resumax.chat.service.PdfService;
import org.apache.catalina.User;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    private final EmbeddingModel embeddingModel;

    private final VectorStore vectorStore;

    private final PdfService pdfService;

    public ChatController(ChatClient.Builder builder,
                          EmbeddingModel embeddingModel,
                          VectorStore vectorStore,
                          PdfService pdfService){

        this.chatClient = builder.build();
        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;
        this.pdfService = pdfService;
    }


    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q") String query){

        String response = chatClient.prompt(query).call().content();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/embedding")
    public void embedding(@RequestParam(value = "q") String query){

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
//
//        return ResponseEntity.ok(response);
    }

    @GetMapping("/rag/{id}")
    public ResponseEntity<UserProfile> rag(@RequestParam(value = "q") String query, @PathVariable(value = "id")String userId){

        UserProfile response = chatClient.prompt()
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
                .entity(ParameterizedTypeReference.forType(UserProfile.class));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf() throws Exception{

        UserProfile profile = new UserProfile();

        profile.setUserId("user_1");
        profile.setName("Salim Saudagar");
        profile.setEmail("salimsaudagar84@gmail.com");
        profile.setLinkedIn("linkedin.com/in/salim-saudagar");
        profile.setLocation("Maharashtra, India");
        profile.setSummary("Backend developer specializing in building scalable APIs and high-performance systems using Spring Boot, Redis, and PostgreSQL.");

// ===== Skills =====
        SkillGroup backend = new SkillGroup();
        backend.setCategory("Backend");
        backend.setSkills(List.of("Java", "Spring Boot", "Microservices"));

        SkillGroup database = new SkillGroup();
        database.setCategory("Database");
        database.setSkills(List.of("PostgreSQL", "Redis"));

        SkillGroup tools = new SkillGroup();
        tools.setCategory("Tools & Technologies");
        tools.setSkills(List.of("Docker", "Git", "Firebase"));

        profile.setSkillGroups(List.of(backend, database, tools));

// ===== Projects =====
        Project project1 = new Project();
        project1.setName("Society Management System");
        project1.setTimeline("Jan 2025 – Mar 2025");
        project1.setDescription(List.of("Developed a full-stack system with JWT authentication"," refresh tokens"," and multi-device session handling."));
        project1.setLink("https://github.com/your-repo/society-app");

        Project project2 = new Project();
        project2.setName("Resume Optimizer (AI-Based Tool)");
        project2.setTimeline("Apr 2026 – Present");
        project2.setDescription(List.of("Built an AI-powered resume optimizer using Spring AI", "RAG pipeline, vector database"," and Redis caching."));
        project2.setLink("https://github.com/your-repo/resume-optimizer");

        profile.setProjects(List.of(project1, project2));

// ===== Experience =====
        Experience exp1 = new Experience();
        exp1.setOrganization("Self / Personal Projects");
        exp1.setTimeline("2025 – Present");
        exp1.setDescription(List.of("Designed and developed scalable backend systems"," focusing on system design","authentication, and performance optimization."));

        profile.setExperiences(List.of(exp1));


// ===== Education =====

        Education education = new Education();

        education.setGrade("4.8 CGPA");
        education.setTimeline("June 2018- May 2025");
        education.setQualification("Bachelors in computer science from xyz college");

        profile.setEducation(education);

        var response = pdfService.generatePdf(profile);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(response);

    }

}
