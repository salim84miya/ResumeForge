package com.springai.resumax.pdf.controller;

import com.springai.resumax.pdf.service.PdfService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {


    private final PdfService pdfService;

    public PdfController(PdfService pdfService){
        this.pdfService = pdfService;
    }


//    @GetMapping("/pdf")
//    public ResponseEntity<byte[]> downloadPdf() throws Exception{
//
//        UserProfile profile = new UserProfile();
//
//        profile.setUserId("user_1");
//        profile.setName("Salim Saudagar");
//        profile.setEmail("salimsaudagar84@gmail.com");
//        profile.setLinkedIn("linkedin.com/in/salim-saudagar");
//        profile.setLocation("Maharashtra, India");
//        profile.setSummary("Backend developer specializing in building scalable APIs and high-performance systems using Spring Boot, Redis, and PostgreSQL.");
//
//// ===== Skills =====
//        SkillGroup backend = new SkillGroup();
//        backend.setCategory("Backend");
//        backend.setSkills(List.of("Java", "Spring Boot", "Microservices"));
//
//        SkillGroup database = new SkillGroup();
//        database.setCategory("Database");
//        database.setSkills(List.of("PostgreSQL", "Redis"));
//
//        SkillGroup tools = new SkillGroup();
//        tools.setCategory("Tools & Technologies");
//        tools.setSkills(List.of("Docker", "Git", "Firebase"));
//
//        profile.setSkillGroups(List.of(backend, database, tools));
//
//// ===== Projects =====
//        Project project1 = new Project();
//        project1.setName("Society Management System");
//        project1.setTimeline("Jan 2025 – Mar 2025");
//        project1.setDescription(List.of("Developed a full-stack system with JWT authentication"," refresh tokens"," and multi-device session handling."));
//        project1.setLink("https://github.com/your-repo/society-app");
//
//        Project project2 = new Project();
//        project2.setName("Resume Optimizer (AI-Based Tool)");
//        project2.setTimeline("Apr 2026 – Present");
//        project2.setDescription(List.of("Built an AI-powered resume optimizer using Spring AI", "RAG pipeline, vector database"," and Redis caching."));
//        project2.setLink("https://github.com/your-repo/resume-optimizer");
//
//        profile.setProjects(List.of(project1, project2));
//
//// ===== Experience =====
//        Experience exp1 = new Experience();
//        exp1.setOrganization("Self / Personal Projects");
//        exp1.setTimeline("2025 – Present");
//        exp1.setDescription(List.of("Designed and developed scalable backend systems"," focusing on system design","authentication, and performance optimization."));
//
//        profile.setExperiences(List.of(exp1));
//
//
//// ===== Education =====
//
//        Education education = new Education();
//
//        education.setGrade("4.8 CGPA");
//        education.setTimeline("June 2018- May 2025");
//        education.setQualification("Bachelors in computer science from xyz college");
//
//        profile.setEducation(education);
//
//        var response = pdfService.generatePdf(profile);
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=resume.pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(response);
//
//    }

}
