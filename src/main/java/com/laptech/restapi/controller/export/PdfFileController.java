package com.laptech.restapi.controller.export;

import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.ProductUnit;
import com.laptech.restapi.service.InvoiceService;
import com.laptech.restapi.service.ProductService;
import com.laptech.restapi.service.ProductUnitService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

@Api("Export PDF Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class PdfFileController {
    private final InvoiceService invoiceService;
    private final ProductService productService;
    private final ProductUnitService productUnitService;

    @PostMapping("invoices/{id}/export")
    public void exportInvoice(@PathVariable("id") String invoiceId,
                              HttpServletResponse response) {
        Invoice invoice = invoiceService.findById(invoiceId);
        Collection<ProductUnit> productUnits = productUnitService.getProductUnitByInvoiceId(invoiceId);
        Collection<Product> products = new ArrayList<>();
        productUnits.forEach(unit -> {
            products.add(productService.findById(unit.getProductId()));
        });

        System.out.println(products);
    }
}
