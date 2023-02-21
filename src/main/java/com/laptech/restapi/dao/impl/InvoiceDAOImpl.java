package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dao.InvoiceDAO;
import com.laptech.restapi.mapper.InvoiceMapper;
import com.laptech.restapi.model.Invoice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */

@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class InvoiceDAOImpl implements InvoiceDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewInvoice}")
    private String INSERT;
    @Value("${sp_UpdateInvoice}")
    private String UPDATE;
    @Value("${sp_UpdateInvoiceStatus}")
    private String UPDATE_ORDER_STATUS;
    @Value("${sp_UpdateInvoicePaymentMethodAndPaidStatus}")
    private String UPDATE_PAYMENT_TYPE_AND_PAID_STATUS;
    @Value("${sp_DeleteInvoice}")
    private String DELETE;
    @Value("${sp_FindAllInvoices}")
    private String QUERY_ALL;
    @Value("${sp_FindAllInvoicesLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindInvoiceById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindInvoiceByUserId}")
    private String QUERY_INVOICES_BY_USER_ID;
    @Value("${sp_FindInvoiceByAddress}")
    private String QUERY_INVOICES_BY_ADDRESS;
    @Value("${sp_FindInvoiceByDate}")
    private String QUERY_INVOICES_BY_DATE;
    @Value("${sp_FindInvoiceByDateRange}")
    private String QUERY_INVOICES_BY_DATE_RANGE;
    @Value("${sp_FindInvoiceByPaymentType}")
    private String QUERY_INVOICES_BY_PAYMENT_TYPE;
    @Value("${sp_FindInvoiceByOrderStatus}")
    private String QUERY_INVOICES_BY_ORDER_STATUS;
    @Value("${sp_FindInvoiceByPaidStatus}")
    private String QUERY_INVOICES_BY_PAID_STATUS;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "user_id=? and address=? and phone=? and payment_amount=? and ship_cost=? and discount_amount=? and " +
            "tax=? and payment_total=? and payment_type=? and is_paid=? and order_status=? and note=?", "tbl_invoice");

    @Override
    public String insert(Invoice invoice) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    invoice.getId(),
                    invoice.getUserId(),
                    invoice.getAddress(),
                    invoice.getPaymentAmount().doubleValue(),
                    invoice.getShipCost(),
                    invoice.getDiscountAmount().doubleValue(),
                    invoice.getTax().doubleValue(),
                    invoice.getPaymentTotal().doubleValue(),
                    invoice.getPaymentType(),
                    invoice.isPaid(),
                    invoice.getOrderStatus().toString(),
                    invoice.getNote()
            );
            return invoice.getId();
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Invoice invoice) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    invoice.getId(),
                    invoice.getUserId(),
                    invoice.getAddress(),
                    invoice.getPhone(),
                    invoice.getPaymentAmount().doubleValue(),
                    invoice.getShipCost(),
                    invoice.getDiscountAmount().doubleValue(),
                    invoice.getTax().doubleValue(),
                    invoice.getPaymentTotal().doubleValue(),
                    invoice.getPaymentType(),
                    invoice.isPaid(),
                    invoice.getOrderStatus().toString(),
                    invoice.getNote()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updateStatus(String invoiceId, OrderStatus status) {
        try {
            return jdbcTemplate.update(
                    UPDATE_ORDER_STATUS,
                    invoiceId,
                    status.toString()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE STATUS] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updatePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PAYMENT_TYPE_AND_PAID_STATUS,
                    invoiceId,
                    paymentType,
                    isPaid
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE PAYMENT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String invoiceId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    invoiceId
            );
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int count() {
        return this.findAll().size();
    }

    @Override
    public boolean isExists(Invoice invoice) {
        try {

            Invoice existsInvoice = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new InvoiceMapper(),
                    invoice.getUserId(),
                    invoice.getAddress(),
                    invoice.getPhone(),
                    invoice.getPaymentAmount().doubleValue(),
                    invoice.getShipCost(),
                    invoice.getDiscountAmount().doubleValue(),
                    invoice.getTax().doubleValue(),
                    invoice.getPaymentTotal().doubleValue(),
                    invoice.getPaymentType(),
                    invoice.isPaid(),
                    invoice.getOrderStatus().toString(),
                    invoice.getNote()
            );
            return existsInvoice != null;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Invoice> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new InvoiceMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new InvoiceMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Invoice findById(String invoiceId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new InvoiceMapper(),
                    invoiceId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoiceByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_USER_ID,
                    new InvoiceMapper(),
                    userId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY USER ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoiceByAddress(String address) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_ADDRESS,
                    new InvoiceMapper(),
                    "%" + address + "%"
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ADDRESS] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoiceByDate(LocalDate date) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_DATE,
                    new InvoiceMapper(),
                    Date.valueOf(date)
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY DATE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoiceByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_DATE_RANGE,
                    new InvoiceMapper(),
                    startDate,
                    endDate
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY DATE RANGE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoiceByPaymentType(String paymentType) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_PAYMENT_TYPE,
                    new InvoiceMapper(),
                    paymentType
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY PAYMENT TYPE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoiceByOrderStatus(OrderStatus status) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_ORDER_STATUS,
                    new InvoiceMapper(),
                    status.toString()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ORDER STATUS] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoiceByPaidStatus(boolean isPaid) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_PAID_STATUS,
                    new InvoiceMapper(),
                    isPaid
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY PAID STATUS] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
