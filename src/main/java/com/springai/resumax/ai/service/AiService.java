package com.springai.resumax.ai.service;


import com.springai.resumax.ai.entity.UserResumeResponse;
import com.springai.resumax.profile.entity.UserEducation;
import com.springai.resumax.profile.entity.UserExperience;
import com.springai.resumax.profile.entity.UserProfile;
import com.springai.resumax.profile.entity.UserProject;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private static final String PROJECT_ID = "projectId";
    private static final String EXPERIENCE_ID = "experienceId";
    private static final String EDUCATION_ID = "educationId";

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public Document buildProfileDocument(UserProfile userProfile){

        String userId = userProfile.getUserId();

        String content = """
                Name: %s
                Location: %s
                Email: %s
                LinkedIn: %s
                """.formatted(
                        userProfile.getName(),
                userProfile.getLocation(),
                userProfile.getEmail(),
                userProfile.getLinkedIn()
        );

        return new Document(
                content,
                Map.of(
                        "userId",userId,
                        "type","profile"
                )
        );

    }

    public void embedProfileDocuments(UserProfile userProfile){

        Document document = buildProfileDocument(userProfile);
        vectorStore.add(List.of(document));
    }

    public void updateEmbedProfileDocuments(UserProfile userProfile){

        deleteEmbedProfileDocuments(userProfile);
        embedProfileDocuments(userProfile);

    }

    public void deleteEmbedProfileDocuments(UserProfile userProfile){

        String userId = userProfile.getUserId();

        deleteByDocsType(userId,"profile");
    }

    public Document buildSkillsDocuments(String skills,String userId){

        String content= """
                Skills: %s
                """.formatted(
                        skills
        );

        return new Document(
                content,
                Map.of("userId",userId,
                        "type","skills"));
    }

    public void embedSkillsDocuments(UserProfile userProfile){

        Document document = buildSkillsDocuments(userProfile.getSkills(),userProfile.getUserId());
        vectorStore.add(List.of(document));
    }

    public void deleteEmbedSkillsDocuments(UserProfile userProfile){
        deleteByDocsType(userProfile.getUserId(),"skills");
    }

    public void updateEmbedSkillsDocuments(UserProfile userProfile){

        deleteEmbedSkillsDocuments(userProfile);
        embedSkillsDocuments(userProfile);
    }

    public Document buildSummaryDocuments(String summary,String userId){

        String content= """
                Summary: %s
                """.formatted(
                summary
        );

        return new Document(
                content,
                Map.of("userId",userId,
                        "type","summary"));
    }

    public void embedSummaryDocuments(UserProfile userProfile){

        Document document = buildSummaryDocuments(userProfile.getSummary(),userProfile.getUserId());
        vectorStore.add(List.of(document));
    }

    public void deleteEmbedSummaryDocuments(UserProfile userProfile){
        deleteByDocsType(userProfile.getUserId(),"summary");
    }

    public void updateEmbedSummaryDocuments(UserProfile userProfile){
        deleteEmbedSummaryDocuments(userProfile);
        embedSummaryDocuments(userProfile);
    }

    private Document buildProjectDocument(UserProject project) {

        String content = """
    Project: %s
    Link: %s
    Description: %s
    Timeline: %s
    """.formatted(
                project.getName(),
                project.getLink(),
                project.getDescription(),
                project.getTimeline()
        );

        return new Document(
                content,
                Map.of(
                        "userId", project.getUserProfile().getUserId(),
                        "docId", PROJECT_ID + project.getId(),
                        "type", "project"
                )
        );
    }

    public void embedProjectDocuments(UserProject project){

        Document document = buildProjectDocument(project);

        vectorStore.add(List.of(document));
    }

    public void updateEmbedProjectDocuments(UserProject newProject){

      deleteEmbedProjectDocuments(newProject);
      embedProjectDocuments(newProject);
    }

    public void deleteEmbedProjectDocuments(UserProject newProject){

        String userId = newProject.getUserProfile().getUserId();

        deleteByDocsId(userId,PROJECT_ID+ newProject.getId());

    }

    private Document buildExperienceDocument(UserExperience experience) {

        String content = """
    Organization: %s
    Description: %s
    Timeline: %s
    """.formatted(
                experience.getOrganization(),
                experience.getDescription(),
                experience.getTimeline()
        );

        return new Document(
                content,
                Map.of(
                        "userId", experience.getUserProfile().getUserId(),
                        "docId", EXPERIENCE_ID + experience.getId(),
                        "type", "experience"
                )
        );
    }

    public void embedExperienceDocuments(UserExperience experience){

        Document experienceDocument = buildExperienceDocument(experience);

        vectorStore.add(List.of(experienceDocument));
    }

    public  void updateEmbedExperienceDocuments(UserExperience newExperience){

        deleteEmbedExperienceDocument(newExperience);
        embedExperienceDocuments(newExperience);
    }

    public void deleteEmbedExperienceDocument(UserExperience experience){

        String userId = experience.getUserProfile().getUserId();

        deleteByDocsId(userId,EXPERIENCE_ID+experience.getId());
    }

    public Document buildEducationDocuments(UserEducation education){

        String userId = education.getUserProfile().getUserId();

        String content = """
                Qualification: %s
                grade: %s
                timeline: %s
                """.formatted(
                    education.getQualification(),
                    education.getGrade(),
                    education.getTimeline()
                );

        return new Document(content,
                Map.of("userId",userId,
                        "docId",EDUCATION_ID+education.getId(),
                        "type","education")
        );
    }

    public void embedEducationDocuments(UserEducation education){

         Document educationDocument = buildEducationDocuments(education);

        vectorStore.add(List.of(educationDocument));
    }

    public void deleteEmbedEducationDocument(UserEducation education){

        String userId = education.getUserProfile().getUserId();

        deleteByDocsId(userId,EDUCATION_ID+education.getId());

    }

    public void updateEmbedEducationDocument(UserEducation education){

        deleteEmbedEducationDocument(education);
        embedEducationDocuments(education);
    }

    public void deleteByDocsType(String userId,String type){

        FilterExpressionBuilder builder = new FilterExpressionBuilder();

        vectorStore.delete(
                builder.and(
                        builder.eq("userId",userId),
                        builder.eq("type",type)
                ).build()
        );
    }
    public void deleteByDocsId(String userId,String docId){

        FilterExpressionBuilder builder = new FilterExpressionBuilder();

        vectorStore.delete(
                builder.and(
                        builder.eq("userId",userId),
                        builder.eq("docId",docId)
                ).build()
        );
    }

    public UserResumeResponse rag(String query,String userId){

        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(
                        CompressionQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build(),
                        RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build(),
                        TranslationQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build()
                )
                .queryExpander(
                        MultiQueryExpander.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build()
                )
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .similarityThreshold(0.3)
                                .filterExpression(
                                        new FilterExpressionBuilder()
                                                .eq("userId",userId)
                                                .build()
                                )
                                .topK(3)
                                .build()
                )
                .documentJoiner(
                        new ConcatenationDocumentJoiner()
                )
                .queryAugmenter(
                        ContextualQueryAugmenter.builder().build()
                )
                .build();

        UserResumeResponse response = chatClient.prompt()
                .advisors(advisorSpec -> {

                    advisorSpec.param(ChatMemory.CONVERSATION_ID,userId);
                })
                .advisors(advisor)
                .user(query)
                .call()
                .entity(ParameterizedTypeReference.forType(UserResumeResponse.class));


        return  response;
    }
}
