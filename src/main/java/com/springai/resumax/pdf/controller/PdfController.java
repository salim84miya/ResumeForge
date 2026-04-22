package com.springai.resumax.pdf.controller;

import com.springai.resumax.ai.entity.UserResumeResponse;
import com.springai.resumax.pdf.service.PdfService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {


    private final PdfService pdfService;

    public PdfController(PdfService pdfService){
        this.pdfService = pdfService;
    }


    @GetMapping("/pdf")
    public ResponseEntity<?> downloadPdf(@RequestBody UserResumeResponse userResumeResponse) throws Exception{

        var response = pdfService.generatePdf(userResumeResponse);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(response);

    }

}
