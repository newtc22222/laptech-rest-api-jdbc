package com.laptech.restapi.service.export;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;

public interface PdfService {
    void writePDFOfImportTicket(HttpServletResponse response, String ticketId) throws IOException, BadElementException;
    void writePDFOfInvoice(HttpServletResponse response, String invoiceId) throws IOException, DocumentException;
}
