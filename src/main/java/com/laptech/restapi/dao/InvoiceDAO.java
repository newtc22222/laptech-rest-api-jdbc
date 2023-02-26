package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dto.filter.InvoiceFilter;
import com.laptech.restapi.model.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface InvoiceDAO extends BaseDAO<Invoice, InvoiceFilter, String> {
    int updateStatus(String invoiceId, OrderStatus status);

    int updatePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid);

    List<Invoice> findInvoiceByUserId(long userId);

    List<Invoice> findInvoiceByAddress(String address);

    List<Invoice> findInvoiceByDate(LocalDate date);

    List<Invoice> findInvoiceByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Invoice> findInvoiceByPaymentType(String paymentType);

    List<Invoice> findInvoiceByOrderStatus(OrderStatus status);

    List<Invoice> findInvoiceByPaidStatus(boolean isPaid); // true -> was paid
}
