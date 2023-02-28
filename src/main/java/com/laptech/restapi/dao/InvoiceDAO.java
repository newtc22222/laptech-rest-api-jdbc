package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dto.filter.InvoiceFilter;
import com.laptech.restapi.model.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface InvoiceDAO extends BaseDAO<Invoice, InvoiceFilter, String> {
    int updateOrderStatus(String invoiceId, OrderStatus status, String updateBy);

    int updatePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid, String updateBy);

    Collection<Invoice> findInvoiceByUserId(long userId);
    Collection<Invoice> findInvoiceByOrderStatus(OrderStatus status);

    Collection<Invoice> findInvoiceByDate(LocalDate date);

    Collection<Invoice> findInvoiceByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
