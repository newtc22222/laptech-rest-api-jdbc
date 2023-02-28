package com.laptech.restapi.controller;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dto.request.InvoiceDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Receipt of user", value = "Invoice Controller")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @ApiOperation(value = "Get all invoices", response = Invoice.class)
    @GetMapping("/invoices")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Collection<Invoice>> getAllInvoices(@RequestParam(required = false, defaultValue = "1") Long page,
                                                              @RequestParam(required = false) Long size,
                                                              @RequestParam(value = "address", required = false) String address,
                                                              @RequestParam(value = "date", required = false) String date,
                                                              @RequestParam(value = "startDate", required = false) String startDate,
                                                              @RequestParam(value = "endDate", required = false) String endDate,
                                                              @RequestParam(value = "paymentType", required = false) String paymentType,
                                                              @RequestParam(value = "status", required = false) String status,
                                                              @RequestParam(value = "isPaid", required = false) String isPaid) {
        if (address == null && date == null && startDate == null && endDate == null
                && paymentType == null && status == null && isPaid == null) {
            return ResponseEntity.ok(invoiceService.findAll(page, size));
        }
        Map<String, String> params = new HashMap<>();
        if (address != null) params.put("address", address);
        if (date != null) params.put("date", date);
        if (startDate != null) params.put("startDate", startDate);
        if (endDate != null) params.put("endDate", endDate);
        if (paymentType != null) params.put("paymentType", paymentType);
        if (status != null) params.put("status", status);
        if (isPaid != null) params.put("isPaid", isPaid);
        return ResponseEntity.ok(invoiceService.filter(params));
    }

    @ApiOperation(value = "Get one invoice with id", response = Invoice.class)
    @GetMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") String invoiceId) {
        return ResponseEntity.ok(invoiceService.findById(invoiceId));
    }

    @ApiOperation(value = "Get all invoices of user", response = Invoice.class)
    @GetMapping("/users/{userId}/invoices")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<List<Invoice>> getInvoicesOfUser(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(invoiceService.getInvoicesOfUser(userId));
    }

    @ApiOperation(value = "Create a new Invoice", response = DataResponse.class)
    @PostMapping("/invoices")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> createNewInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return DataResponse.success(
                "Create new invoice",
                invoiceService.insert(InvoiceDTO.transform(invoiceDTO))
        );
    }

    @ApiOperation(value = "Update all information of Invoice", response = BaseResponse.class)
    @PutMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateInvoice(@PathVariable("id") String invoiceId,
                                                      @RequestBody InvoiceDTO invoiceDTO) {
        invoiceService.update(InvoiceDTO.transform(invoiceDTO), invoiceId);
        return DataResponse.success("Update invoice");
    }

    @ApiOperation(value = "Update payment method and paid status of invoice", response = BaseResponse.class)
    @PatchMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateInvoicePaymentMethodAndPaidStatus(@PathVariable("id") String invoiceId,
                                                                                @RequestBody Map<String, String> request) {
        invoiceService.updateInvoicePaymentMethodAndPaidStatus(
                invoiceId,
                request.get("paymentType"),
                Boolean.parseBoolean(request.get("isPaid")), );
        return DataResponse.success("Update payment method and paid status");
    }

    @ApiOperation(value = "Update the delivery status of Invoice", response = BaseResponse.class)
    @PutMapping("/invoices/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateInvoiceStatus(@PathVariable("id") String invoiceId,
                                                            @RequestBody Map<String, String> body) {
        invoiceService.updateStatus(invoiceId, OrderStatus.valueOf(body.get("status").trim().toUpperCase()), );
        return DataResponse.success("Update invoice's order status");
    }

    @ApiOperation(value = "Remove an invoice", response = BaseResponse.class)
    @DeleteMapping("/invoices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> deleteInvoice(@PathVariable("id") String invoiceId) {
        invoiceService.delete(invoiceId);
        return DataResponse.success("Delete invoice");
    }
}
