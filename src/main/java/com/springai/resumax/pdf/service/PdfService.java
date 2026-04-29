package com.springai.resumax.pdf.service;

import com.springai.resumax.ai.entity.*;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfCell;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.openpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

@Service
public class PdfService {

    public byte[] generatePdf(UserResumeResponse userResume) throws Exception{


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document,out);

        document.open();


        //Top name header font
        Font headerFont = new Font(Font.HELVETICA,14,Font.BOLD);

        //section heading font
        Font sectionHeadingFont = new Font(Font.HELVETICA,12,Font.BOLD);

        //section title font
        Font sectionTitleFont = new Font(Font.HELVETICA,9,Font.BOLD);

        //project title font
        Font projectTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        projectTitleFont.setColor(0, 0, 0); // pure black

        //section content font
        Font sectionContentFont = new Font(Font.HELVETICA,9);


        //timeline font
        Font timelineFont = new Font(Font.HELVETICA,9);
        timelineFont.setColor(120,120,120);

        //Name
        Paragraph section = new Paragraph(userResume.getName().toUpperCase(Locale.ROOT),headerFont);
        section.setSpacingAfter(1);
        section.setAlignment(Element.ALIGN_CENTER);

        document.add(section);

        StringBuilder topKeywordFormatted = new StringBuilder();

        for(int i=0; i<userResume.getKeyword().size(); i++){

            if(i==userResume.getKeyword().size()-1){
                topKeywordFormatted.append(userResume.getKeyword().get(i));
            }else{
                topKeywordFormatted.append(userResume.getKeyword().get(i)+" | ");
            }
        }

        //keyword
        Paragraph keywordSection = new Paragraph(
                topKeywordFormatted.toString(),
                new Font(Font.HELVETICA,12));

        keywordSection.setAlignment(Element.ALIGN_CENTER);

        document.add(keywordSection);

        //contacts

        Paragraph contactSection = new Paragraph(
                userResume.getEmail()+
                        " | "
                        +userResume.getLinkedIn()+
                        " | "
                        +userResume.getLocation(),
                sectionContentFont);

        contactSection.setSpacingAfter(20);
        contactSection.setAlignment(Element.ALIGN_CENTER);

        document.add(contactSection);


        // Summary
        Paragraph summaryHeading = new Paragraph("SUMMARY",sectionHeadingFont);
        summaryHeading.setSpacingAfter(2);

        document.add(summaryHeading);

        //divider
        LineSeparator line = new LineSeparator();
        line.setLineWidth(1f);

        document.add(line);

        //summary content
        Paragraph summarySection = new Paragraph(userResume.getSummary(),sectionContentFont);
        summarySection.setSpacingBefore(2);
        summarySection.setSpacingAfter(15);
        summarySection.setAlignment(Element.ALIGN_LEFT);

        document.add(summarySection);


        // Projects

        //heading
        Paragraph projectsHeading = new Paragraph("PROJECTS",sectionHeadingFont);
        projectsHeading.setSpacingAfter(2);

        document.add(projectsHeading);

        //divider
        LineSeparator projectLine = new LineSeparator();
        projectLine.setLineWidth(1f);

        document.add(projectLine);

        //content
        for(Project p :userResume.getProjects()){

            //spacing above
            setLineSpacing(document,2,2);

            //project name and timeline
            setSectionTitle(document, p.getName(),p.getTimeline(),projectTitleFont,sectionContentFont,p.getLink());

            Paragraph shortTitle = new Paragraph(p.getShortTitle(),sectionContentFont);

            shortTitle.setIndentationLeft(3f);
            document.add(shortTitle);

            setLineSpacing(document,1,1);

            //project details
            List bulletList = new List(List.UNORDERED);

            bulletList.setIndentationLeft(10);

            for(String item : p.getDescription()){
                bulletList.add(new ListItem(item,sectionContentFont));
            }

            document.add(bulletList);

        }


        //spacing above
        setLineSpacing(document,3,3);
        // Skills

        //heading
        Paragraph skillsHeading = new Paragraph("SKILLS",sectionHeadingFont);
        skillsHeading.setSpacingAfter(2);

        document.add(skillsHeading);

        //divider
        LineSeparator skillsLine = new LineSeparator();
        skillsLine.setLineWidth(1f);

        document.add(skillsLine);

        //spacing above
        setLineSpacing(document,2,2);

        for (SkillGroup group : userResume.getSkillGroups()) {

            Paragraph para = new Paragraph();

            para.add(new Chunk(group.getCategory()+": ",sectionTitleFont));
            para.add(new Chunk(String.join(", ", group.getSkills()),sectionContentFont));

            document.add(para);
        }

        //spacing below
        setLineSpacing(document,2,2);



        if( userResume.getExperiences()!=null && !userResume.getExperiences().isEmpty()){
            setLineSpacing(document,2,2);

            // Experience

            //heading
            Paragraph experienceHeading = new Paragraph("EXPERIENCE",sectionHeadingFont);
            experienceHeading.setSpacingAfter(2);

            document.add(experienceHeading);

            //divider
            LineSeparator experienceLine = new LineSeparator();
            experienceLine.setLineWidth(1f);

            document.add(experienceLine);


            //content
            for (Experience e : userResume.getExperiences()) {


                //organization
                setSectionTitle(document,e.getOrganization(),e.getTimeline(),sectionTitleFont,sectionContentFont,null);

                //work in bullet points
                List bulletList = new List(List.UNORDERED);

                bulletList.setIndentationLeft(10);

                for(String item: e.getDescription()){
                    bulletList.add(new ListItem(item,sectionContentFont));
                }

                document.add(bulletList);
            }
        }


        setLineSpacing(document,2,2);

        // Education

        //heading
        Paragraph educationHeading = new Paragraph("EDUCATION",sectionHeadingFont);
        educationHeading.setSpacingAfter(2);

        document.add(educationHeading);

        //divider
        LineSeparator educationLine = new LineSeparator();
        educationLine.setLineWidth(1f);

        document.add(educationLine);

        //content

        for(Education e : userResume.getEducation()){

            setSectionTitle(
                    document,
                    e.getQualification()+": "+e.getGrade(),
                    e.getTimeline(),
                    sectionContentFont,sectionContentFont,null);
        }

        setLineSpacing(document,2,2);

        // Key Achievements

        //heading
        Paragraph achievementHeading = new Paragraph("KEY ACHIEVEMENTS",sectionHeadingFont);
        projectsHeading.setSpacingAfter(2);

        document.add(achievementHeading);

        setLineSpacing(document,2,2);

        //divider
        LineSeparator achievementLine = new LineSeparator();
        projectLine.setLineWidth(1f);

        document.add(achievementLine);

        //content



        PdfPTable achievementTable = new PdfPTable(3); // 3 achievements per row
        achievementTable.setWidthPercentage(100);
        achievementTable.setSpacingBefore(10f);

        for (String achievement : userResume.getKeyAchievements()) {
            achievementTable.addCell(createAchievementCell(
                    achievement,
                    "src/main/resources/static/icon.png"
            ));
        }

        int remaining = userResume.getKeyAchievements().size() % 3;


        if (remaining != 0) {

            PdfPCell emptyCell = new PdfPCell(new Phrase(""));
            emptyCell.setBorder(Rectangle.NO_BORDER);
            for (int i = 0; i < (3 - remaining); i++) {
                achievementTable.addCell(emptyCell);
            }
        }

        document.add(achievementTable);

        document.close();

        return out.toByteArray();
    }


    public void setLineSpacing(Document doc,int before,int after){
        Paragraph para = new Paragraph();
        para.setSpacingBefore(before);
        para.setSpacingAfter(after);

        doc.add(para);
    }

    public void setSectionTitle(Document doc,String leftContent,String rightContent,Font leftFont,Font rightFont,String link){
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 1});

        // Left → Project Name

        PdfPCell left ;

        if(link!=null && !link.isBlank()){

            Chunk projectChunk = new Chunk(leftContent,leftFont);
            projectChunk.setAnchor(link);

            Paragraph projectName = new Paragraph();

            projectName.add(projectChunk);

             left = new PdfPCell(projectName);
            left.setBorder(Rectangle.NO_BORDER);
        }else{
           left = new PdfPCell(new Paragraph(leftContent, leftFont));
            left.setBorder(Rectangle.NO_BORDER);
        }

        // Right → Timeline
        PdfPCell right = new PdfPCell(new Paragraph(rightContent, rightFont));
        right.setBorder(Rectangle.NO_BORDER);
        right.setHorizontalAlignment(Element.ALIGN_RIGHT);


        table.setSpacingAfter(4);
        table.setSpacingBefore(4);

        table.addCell(left);
        table.addCell(right);

        doc.add(table);
    }

    private PdfPCell createAchievementCell(String text, String iconPath) throws Exception {

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);

        // Inner table to align icon + text horizontally
        PdfPTable innerTable = new PdfPTable(2);
        innerTable.setWidths(new float[]{1, 4}); // icon : text ratio
        innerTable.setWidthPercentage(100);

        // Icon
        Image icon = Image.getInstance(iconPath);
        icon.scaleToFit(12, 12);

        PdfPCell iconCell = new PdfPCell(icon);
        iconCell.setBorder(Rectangle.NO_BORDER);
        iconCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Font achievementFont = new Font(Font.HELVETICA, 9);

        // Text
        PdfPCell textCell = new PdfPCell(new Phrase(text,achievementFont));
        textCell.setBorder(Rectangle.NO_BORDER);
        textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        innerTable.addCell(iconCell);
        innerTable.addCell(textCell);

        cell.addElement(innerTable);

        return cell;
    }
}
