package com.laptech.restapi.controller;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dto.request.InvoiceDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.service.InvoiceService;
import com.laptech.restapi.service.email.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(description = "Receipt of user's order", tags = "Invoice Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class InvoiceController {
    private final InvoiceService invoiceService;

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
    public ResponseEntity<DataResponse> getAllInvoices(@RequestParam(required = false) Long userId,
                                                       @RequestParam(required = false) String address,
                                                       @RequestParam(required = false) String phone,
                                                       @RequestParam(required = false) BigDecimal paymentAmount,
                                                       @RequestParam(required = false) Double shipCost,
                                                       @RequestParam(required = false) BigDecimal discountAmount,
                                                       @RequestParam(required = false) BigDecimal tax,
                                                       @RequestParam(required = false) BigDecimal paymentTotal,
                                                       @RequestParam(required = false) String paymentType,
                                                       @RequestParam(required = false) Boolean isPaid,
                                                       @RequestParam(required = false) String orderStatus,
                                                       @RequestParam(required = false) String note,
                                                       @RequestParam(required = false) String date,
                                                       @RequestParam(required = false) String startDate,
                                                       @RequestParam(required = false) String endDate,
                                                       @RequestParam(required = false) String createdDate,
                                                       @RequestParam(required = false) String modifiedDate,
                                                       @RequestParam(required = false) String deletedDate,
                                                       @RequestParam(required = false) Boolean isDel,
                                                       @RequestParam(required = false) String updateBy,
                                                       @RequestParam(required = false) String sortBy,
                                                       @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("address", address);
        params.put("phone", phone);
        params.put("paymentAmount", paymentAmount);
        params.put("shipCost", shipCost);
        params.put("discountAmount", discountAmount);
        params.put("tax", tax);
        params.put("paymentTotal", paymentTotal);
        params.put("paymentType", paymentType);
        params.put("isPaid", isPaid);
        params.put("orderStatus", orderStatus);
        params.put("note", note);
        params.put("date", date);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);
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
    public ResponseEntity<DataResponse> getInvoicesOfUser(@PathVariable("userId") long userId) {
        return DataResponse.getCollectionSuccess(
                "Get invoices of user",
                invoiceService.getInvoicesOfUser(userId)
        );
    }

    @ApiOperation(value = "Create a new Invoice", response = DataResponse.class)
    @PostMapping("/invoices")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> createNewInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        Invoice newInvoice = invoiceService.insert(InvoiceDTO.transform(invoiceDTO));
        return DataResponse.success("Create new invoice", newInvoice);
    }

    @ApiOperation(value = "Update all information of Invoice", response = BaseResponse.class)
    @PutMapping("/invoices/{id}")
    public ResponseEntity<BaseResponse> updateInvoice(@PathVariable("id") String invoiceId,
                                                      @Valid @RequestBody InvoiceDTO invoiceDTO) {
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
