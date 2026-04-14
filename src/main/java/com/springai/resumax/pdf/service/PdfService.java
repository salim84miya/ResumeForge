package com.springai.resumax.pdf.service;

import org.springframework.stereotype.Service;

@Service
public class PdfService {

//    public byte[] generatePdf(UserProfile userProfile) throws Exception{
//
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfWriter.getInstance(document,out);
//
//        document.open();
//
//
//        //Top name header font
//        Font headerFont = new Font(Font.HELVETICA,14,Font.BOLD);
//
//        //section heading font
//        Font sectionHeadingFont = new Font(Font.HELVETICA,12,Font.BOLD);
//
//        //section title font
//        Font sectionTitleFont = new Font(Font.HELVETICA,9,Font.BOLD);
//
//        //section content font
//        Font sectionContentFont = new Font(Font.HELVETICA,9);
//
//        //timeline font
//        Font timelineFont = new Font(Font.HELVETICA,9);
//        timelineFont.setColor(120,120,120);
//
//        //Name
//        Paragraph section = new Paragraph(userProfile.getName().toUpperCase(Locale.ROOT),headerFont);
//        section.setSpacingAfter(1);
//        section.setAlignment(Element.ALIGN_CENTER);
//
//        document.add(section);
//
//        //contacts
//
//        Paragraph contactSection = new Paragraph(
//                userProfile.getEmail()+
//                        " | "
//                        +userProfile.getLinkedIn()+
//                        " | "
//                        +userProfile.getLocation(),
//                sectionContentFont);
//
//        contactSection.setSpacingAfter(20);
//        contactSection.setAlignment(Element.ALIGN_CENTER);
//
//        document.add(contactSection);
//
//
//        // Summary
//        Paragraph summaryHeading = new Paragraph("SUMMARY",sectionHeadingFont);
//        summaryHeading.setSpacingAfter(2);
//
//        document.add(summaryHeading);
//
//        //divider
//        LineSeparator line = new LineSeparator();
//        line.setLineWidth(1f);
//
//        document.add(line);
//
//        //summary content
//        Paragraph summarySection = new Paragraph(userProfile.getSummary(),sectionContentFont);
//        summarySection.setSpacingBefore(2);
//        summarySection.setSpacingAfter(15);
//        summarySection.setAlignment(Element.ALIGN_LEFT);
//
//        document.add(summarySection);
//
//        // Skills
//
//        //heading
//        Paragraph skillsHeading = new Paragraph("SKILLS",sectionHeadingFont);
//        skillsHeading.setSpacingAfter(2);
//
//        document.add(skillsHeading);
//
//        //divider
//        LineSeparator skillsLine = new LineSeparator();
//        skillsLine.setLineWidth(1f);
//
//        document.add(skillsLine);
//
//        //spacing above
//        setLineSpacing(document,2,2);
//
//        for (SkillGroup group : userProfile.getSkillGroups()) {
//
//            Paragraph para = new Paragraph();
//
//            para.add(new Chunk(group.getCategory()+": ",sectionTitleFont));
//            para.add(new Chunk(String.join(", ", group.getSkills()),sectionContentFont));
//
//            document.add(para);
//        }
//
//        //spacing below
//        setLineSpacing(document,2,2);
//
//        // Projects
//
//        //heading
//        Paragraph projectsHeading = new Paragraph("PROJECTS",sectionHeadingFont);
//        projectsHeading.setSpacingAfter(2);
//
//        document.add(projectsHeading);
//
//        //divider
//        LineSeparator projectLine = new LineSeparator();
//        projectLine.setLineWidth(1f);
//
//        document.add(projectLine);
//
//        //content
//        for(Project p :userProfile.getProjects()){
//
//            //project name and timeline
//            setSectionTitle(document, p.getName(),p.getTimeline(),sectionTitleFont,sectionContentFont);
//
//            //project details
//            List bulletList = new List(List.UNORDERED);
//
//            bulletList.setIndentationLeft(10);
//
//            for(String item:p.getDescription()){
//                bulletList.add(new ListItem(item,sectionContentFont));
//            }
//
//            document.add(bulletList);
//
//        }
//
//        //spacing below
//
//        setLineSpacing(document,2,2);
//
//        // Experience
//
//        //heading
//        Paragraph experienceHeading = new Paragraph("EXPERIENCE",sectionHeadingFont);
//        experienceHeading.setSpacingAfter(2);
//
//        document.add(experienceHeading);
//
//        //divider
//        LineSeparator experienceLine = new LineSeparator();
//        experienceLine.setLineWidth(1f);
//
//        document.add(experienceLine);
//
//
//        //content
//        for (Experience e : userProfile.getExperiences()) {
//
//
//            //organization
//            setSectionTitle(document,e.getOrganization(),e.getTimeline(),sectionTitleFont,sectionContentFont);
//
//            //work in bullet points
//            List bulletList = new List(List.UNORDERED);
//
//            bulletList.setIndentationLeft(10);
//
//            for(String item: e.getDescription()){
//                bulletList.add(new ListItem(item,sectionContentFont));
//            }
//
//            document.add(bulletList);
//        }
//
//        setLineSpacing(document,2,2);
//
//        // Education
//
//        //heading
//        Paragraph educationHeading = new Paragraph("EDUCATION",sectionHeadingFont);
//        educationHeading.setSpacingAfter(2);
//
//        document.add(educationHeading);
//
//        //divider
//        LineSeparator educationLine = new LineSeparator();
//        educationLine.setLineWidth(1f);
//
//        document.add(educationLine);
//
//        //content
//        setSectionTitle(
//                document,
//                userProfile.getEducation().getQualification()+": "+userProfile.getEducation().getGrade(),
//                userProfile.getEducation().getTimeline(),
//                sectionContentFont,sectionContentFont);
//
//
//        document.close();
//
//        return out.toByteArray();
//    }
//
//
//    public void setLineSpacing(Document doc,int before,int after){
//        Paragraph para = new Paragraph();
//        para.setSpacingBefore(before);
//        para.setSpacingAfter(after);
//
//        doc.add(para);
//    }
//
//    public void setSectionTitle(Document doc,String leftContent,String rightContent,Font leftFont,Font rightFont){
//        PdfPTable table = new PdfPTable(2);
//        table.setWidthPercentage(100);
//        table.setWidths(new float[]{3, 1});
//
//        // Left → Project Name
//        PdfPCell left = new PdfPCell(new Phrase(leftContent, leftFont));
//        left.setBorder(Rectangle.NO_BORDER);
//
//        // Right → Timeline
//        PdfPCell right = new PdfPCell(new Phrase(rightContent, rightFont));
//        right.setBorder(Rectangle.NO_BORDER);
//        right.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//
//        table.setSpacingAfter(4);
//        table.setSpacingBefore(4);
//
//        table.addCell(left);
//        table.addCell(right);
//
//        doc.add(table);
//    }
}
