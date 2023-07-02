package com.laptech.restapi.service.export.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.laptech.restapi.dao.*;
import com.laptech.restapi.model.*;
import com.laptech.restapi.service.export.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class PdfServiceImpl implements PdfService {
    private final ProductDAO productDAO;
    private final ProductImageDAO productImageDAO;
    private final ImportProductDAO importProductDAO;
    private final InvoiceDAO invoiceDAO;
    private final ProductUnitDAO productUnitDAO;
    private final UserDAO userDAO;

    @Override
    public void writePDFOfImportTicket(HttpServletResponse response, String ticketId) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"ticket_" + LocalDateTime.now().toLocalTime() + ".pdf\"");
        // data
        ImportProduct importProduct = importProductDAO.findById(ticketId);
        Product product = productDAO.findById(importProduct.getProductId());
        ProductImage productImage = new ArrayList<>(productImageDAO.findProductImageByProductId(product.getId())).get(0);

        // attributes
        Map<String, String> attributes = new HashMap<>();
        attributes.put("Creator", importProduct.getUpdateBy());
        attributes.put("Title", "Import Ticket ");
        attributes.put("Subject", "Import ticket of product " + product.getName() + " in date " + importProduct.getImportedDate());

        // contents
        Paragraph documentTitle = new Paragraph("HÓA ĐƠN NHẬP HÀNG", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));

        this.writePdfFile(response, attributes,
                documentTitle
        );
    }

    @Override
    public void writePDFOfInvoice(HttpServletResponse response, String invoiceId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"invoice_" + LocalDateTime.now().toLocalTime() + ".pdf\"");
        // data
        Invoice invoice = invoiceDAO.findById(invoiceId);
        User user = userDAO.findById(invoice.getUserId());
        Collection<ProductUnit> productUnits = productUnitDAO.findProductUnitByInvoiceId(invoiceId);

        // attributes
        Map<String, String> attributes = new HashMap<>();
        attributes.put("Creator", user.getName());
        attributes.put("Title", "Order ");
        attributes.put("Subject", invoice.getNote());

        // contents
        BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Paragraph documentTitle = new Paragraph("ORDER RECEIPT", FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLUE));
        documentTitle.setPaddingTop(10f);
        documentTitle.setSpacingAfter(10f);
        documentTitle.setAlignment(Element.ALIGN_CENTER);
        Paragraph userName = new Paragraph("Customer: " + user.getName(), FontFactory.getFont(String.valueOf(baseFont), 12));
        Paragraph address = new Paragraph("Shipping address: " + invoice.getAddress(), FontFactory.getFont(String.valueOf(baseFont), 12));
        Paragraph phone = new Paragraph("Shipping phone: " + invoice.getPhone(), FontFactory.getFont(String.valueOf(baseFont), 12));
        Paragraph dateOrder = new Paragraph("Date order: " + invoice.getCreatedDate().toString(), FontFactory.getFont(String.valueOf(baseFont), 12));
        Paragraph datePrint = new Paragraph("Date print bill: " + LocalDateTime.now(), FontFactory.getFont(String.valueOf(baseFont), 12));
        datePrint.setSpacingAfter(10f);
        // table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Image");
        table.addCell("Product name");
        table.addCell("Quantity");
        table.addCell("List price");
        table.addCell("Discount price");
        table.addCell("Total price");

        AtomicInteger index = new AtomicInteger(1);
        productUnits.forEach(productUnit -> {
            Product product = productDAO.findById(productUnit.getProductId());
            String url = new ArrayList<>(productImageDAO
                    .findProductImageByProductId(productUnit.getProductId()))
                    .get(0)
                    .getUrl();
            String totalPrice = String.valueOf(productUnit.getDiscountPrice().multiply(BigDecimal.valueOf(productUnit.getQuantity())).longValue());
            table.addCell(String.valueOf(index.getAndIncrement()));
            try {
                Image imageView = Image.getInstance(new URL(url));
                imageView.scaleAbsolute(200, 200);
                table.addCell(imageView);
            } catch (BadElementException | IOException e) {
                table.addCell("");
            }
            table.addCell(product.getName());
            table.addCell(String.valueOf(productUnit.getQuantity()));
            table.addCell(String.valueOf(productUnit.getPrice().longValue()));
            table.addCell(String.valueOf(productUnit.getDiscountPrice().longValue()));
            table.addCell(totalPrice);
        });
        // footer
        Paragraph footerTotalPrice = new Paragraph("TOTAL PAYMENT: " + invoice.getPaymentTotal().longValue(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        footerTotalPrice.setSpacingBefore(10f);
        footerTotalPrice.setSpacingAfter(10f);
        Paragraph footerThanks = new Paragraph("Thank you for your choosing LAPTECH! Hope you have a good day!",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.GRAY));
        footerThanks.setAlignment(Element.ALIGN_CENTER);

        Element[] elements = new Element[] {
                documentTitle,
                userName,
                address,
                phone,
                dateOrder,
                datePrint,
                table,
                footerTotalPrice,
                footerThanks
        };

        this.writePdfFile(response, attributes, elements);
    }

    private void writePdfFile(HttpServletResponse response, Map<String, String> attributes, Element... contents) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // --- attributes ---
            document.addAuthor("LAPTECH");
            document.addCreationDate();
            document.addCreator(Optional.ofNullable(attributes.get("Creator")).orElse("Laptech_SYSTEM"));
            document.addTitle(Optional.ofNullable(attributes.get("Title")).orElse("Receipt ") + LocalDateTime.now());
            document.addSubject("Nothing subject here!");

            // --- writing ---
            Arrays.stream(contents).forEach(content -> {
                try {
                    document.add(content);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
            });

            // --- close ---
            document.close();
            writer.close();
        } catch (IOException | DocumentException err) {
            err.printStackTrace();
        }
    }
}
