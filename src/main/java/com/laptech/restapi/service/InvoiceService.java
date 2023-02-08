package com.laptech.restapi.service;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.model.Invoice;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface InvoiceService extends BaseService<Invoice, String> {
    void updateStatus(String invoiceId, OrderStatus status);

    void updateInvoicePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid);

    List<Invoice> getInvoicesOfUser(long userId);

    /**
     * Filter options:
     * - address
     * - date | start date + end date
     * - payment type
     * - order status
     * - payment status
     */
    Set<Invoice> filter(Map<String, String> params);
}
