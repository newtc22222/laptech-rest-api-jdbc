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

import java.util.HashMap;
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
    public ResponseEntity<DataResponse> getAllInvoices(@RequestParam(required = false) String sortBy,
                                                       @RequestParam(required = false) String sortDir,
                                                       @RequestParam(required = false) Long page,
                                                       @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all invoices",
                invoiceService.count(),
                invoiceService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get invoice with filter", response = Invoice.class)
    @GetMapping("/invoices/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getAllInvoices(@RequestParam(value = "address", required = false) String address,
                                                              @RequestParam(value = "date", required = false) String date,
                                                              @RequestParam(value = "startDate", required = false) String startDate,
                                                              @RequestParam(value = "endDate", required = false) String endDate,
                                                              @RequestParam(value = "paymentType", required = false) String paymentType,
                                                              @RequestParam(value = "status", required = false) String status,
                                                              @RequestParam(value = "isPaid", required = false) String isPaid) {
        Map<String, Object> params = new HashMap<>();
        params.put("address", address);
        params.put("date", date);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("paymentType", paymentType);
        params.put("status", status);
        params.put("isPaid", isPaid);
        return DataResponse.getCollectionSuccess(
                "Get invoice with filter",
                invoiceService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get one invoice with id", response = Invoice.class)
    @GetMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<DataResponse> getInvoiceById(@PathVariable("id") String invoiceId) {
        return DataResponse.getObjectSuccess(
                "Get invoice",
                invoiceService.findById(invoiceId)
        );
    }

    @ApiOperation(value = "Get all invoices of user", response = Invoice.class)
    @GetMapping("/users/{userId}/invoices")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<DataResponse> getInvoicesOfUser(@PathVariable("userId") long userId) {
        return DataResponse.getCollectionSuccess(
                "Get invoices of user",
                invoiceService.getInvoicesOfUser(userId)
        );
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
                                                                                @RequestBody Map<String, String> body) {
        invoiceService.updateInvoicePaymentMethodAndPaidStatus(
                invoiceId,
                body.get("paymentType"),
                Boolean.parseBoolean(body.get("isPaid")), body.get("updateBy"));
        return DataResponse.success("Update payment method and paid status");
    }

    @ApiOperation(value = "Update the delivery status of Invoice", response = BaseResponse.class)
    @PutMapping("/invoices/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateInvoiceStatus(@PathVariable("id") String invoiceId,
                                                            @RequestBody Map<String, String> body) {
        invoiceService.updateStatus(
                invoiceId,
                OrderStatus.valueOf(body.get("status").toUpperCase()),
                body.get("updateBy"));
        return DataResponse.success("Update invoice's order status");
    }

    @ApiOperation(value = "Remove an invoice", response = BaseResponse.class)
    @DeleteMapping("/invoices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> deleteInvoice(@PathVariable("id") String invoiceId,
                                                      @RequestBody(required = false) Map<String, String> body) {
        invoiceService.delete(invoiceId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete invoice");
    }
}
