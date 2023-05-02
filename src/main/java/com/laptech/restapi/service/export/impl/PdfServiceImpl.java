package com.laptech.restapi.service.export.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.laptech.restapi.service.export.PdfService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class PdfServiceImpl implements PdfService {
    @Override
    public void writeDataToPDF(HttpServletResponse response) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // --- attributes ---
            document.addAuthor("Bla bla");
            document.addCreationDate();
            document.addCreator("Some body");
            document.addTitle("Test build PDF");
            document.addSubject("Nothing subject here!");

            // --- writing ---
            document.add(new Paragraph("THIS IS YOUR BILL!"));
            document.add(new Paragraph(new Date().toString()));
            // image
            Image image = Image.getInstance(new URL("https://product.hstatic.net/1000230642/product/hsm000500reu__2__80abe47bede4441782a5b31ef7f83602.jpg"));
            document.add(image);

            // --- close ---
            document.close();
            writer.close();
        } catch (IOException | DocumentException err) {
            err.printStackTrace();
        }
    }
}
