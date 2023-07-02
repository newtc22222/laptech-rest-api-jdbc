package com.laptech.restapi.controller.export;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.laptech.restapi.service.export.PdfService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "Export PDF Controller", description = "Export order or import file")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class PdfFileController {
    private final PdfService pdfService;

    @GetMapping("invoices/{id}/export-pdf")
    public void exportInvoice(@PathVariable("id") String invoiceId,
                              HttpServletResponse response) throws DocumentException, IOException {
        pdfService.writePDFOfInvoice(response, invoiceId);
    }

    @GetMapping("imported/{id}/export-pdf")
    public void exportImportTicket(@PathVariable("id") String importedProductId,
                                   HttpServletResponse response) throws BadElementException, IOException {
        pdfService.writePDFOfImportTicket(response, importedProductId);
    }
}
