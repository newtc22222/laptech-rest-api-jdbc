package com.laptech.restapi.service;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.model.Invoice;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface InvoiceService extends BaseService<Invoice, String> {
    void updateStatus(String invoiceId, OrderStatus status, String updateBy);

    void updateInvoicePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid, String updateBy);

    Collection<Invoice> getInvoicesOfUser(long userId);
}
