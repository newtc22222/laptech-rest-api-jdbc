package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.model.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface InvoiceDAO extends BaseDAO<Invoice, String> {
    int updateStatus(String invoiceId, OrderStatus status);

    int updateInvoicePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid);

    @Deprecated
    int updatePaidStatus(String invoiceId, boolean isPaid); // true -> was paid

    List<Invoice> findInvoicesByUserId(long userId);

    List<Invoice> findInvoicesByAddress(String address);

    List<Invoice> findInvoicesByDate(LocalDate date);

    List<Invoice> findInvoicesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Invoice> findInvoicesByPaymentType(String paymentType);

    List<Invoice> findInvoicesByOrderStatus(OrderStatus status);

    List<Invoice> findInvoicesByPaidStatus(boolean isPaid); // true -> was paid
}
