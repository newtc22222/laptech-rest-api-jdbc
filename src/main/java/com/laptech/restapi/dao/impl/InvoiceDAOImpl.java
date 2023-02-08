package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dao.InvoiceDAO;
import com.laptech.restapi.mapper.InvoiceMapper;
import com.laptech.restapi.model.Invoice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
@PropertySource("query.properties")
public class InvoiceDAOImpl implements InvoiceDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Query String
    private final String TABLE_NAME = "joshua_tbl_invoice";
    private final String INSERT =
            String.format("insert into %s values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set user_id=?, address=?, payment_amount=?, ship_cost=?, discount_amount=?, " +
            "tax=?, payment_total=?, payment_type=?, is_paid=?, order_status=?, " +
            "note=?, modified_date=now() where id=?", TABLE_NAME);
    private final String UPDATE_ORDER_STATUS = String.format("update %s set status=?, modified_date=now() where id=?", TABLE_NAME);
    private final String UPDATE_PAYMENT_TYPE_AND_PAID_STATUS =
            String.format("update %s set payment_type=?, is_paid=?, modified_date=now() where id=?", TABLE_NAME);
    private final String UPDATE_PAID_STATUS = String.format("update %s set is_paid=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "user_id=? and address=? and payment_amount=? and ship_cost=? and discount_amount=? and " +
            "tax=? and payment_total=? and payment_type=? and is_paid=? and order_status=? and note=?", TABLE_NAME);

    private final String QUERY_INVOICES_BY_USER_ID =
            String.format("select * from %s where user_id=?", TABLE_NAME);
    private final String QUERY_INVOICES_BY_ADDRESS =
            String.format("select * from %s where address like ?", TABLE_NAME);
    private final String QUERY_INVOICES_BY_DATE =
            String.format("select * from %s where cast(created_date as date) = ?", TABLE_NAME);
    private final String QUERY_INVOICES_BY_DATE_RANGE =
            String.format("select * from %s where created_date between ? and ?", TABLE_NAME);
    private final String QUERY_INVOICES_BY_PAYMENT_TYPE =
            String.format("select * from %s where payment_type=?", TABLE_NAME);
    private final String QUERY_INVOICES_BY_ORDER_STATUS =
            String.format("select * from %s where status=?", TABLE_NAME);
    private final String QUERY_INVOICES_BY_PAID_STATUS =
            String.format("select * from %s where is_paid=?", TABLE_NAME);

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
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Invoice invoice) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
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
                    invoice.getNote(),
                    invoice.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int updateStatus(String invoiceId, OrderStatus status) {
        try {
            return jdbcTemplate.update(
                    UPDATE_ORDER_STATUS,
                    status.toString(),
                    invoiceId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int updateInvoicePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PAYMENT_TYPE_AND_PAID_STATUS,
                    paymentType,
                    isPaid,
                    invoiceId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int updatePaidStatus(String invoiceId, boolean isPaid) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PAID_STATUS,
                    isPaid,
                    invoiceId
            );
        } catch (DataAccessException err) {
            log.error(err);
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
            log.error(err);
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
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoicesByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_USER_ID,
                    new InvoiceMapper(),
                    userId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoicesByAddress(String address) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_ADDRESS,
                    new InvoiceMapper(),
                    "%" + address + "%"
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoicesByDate(LocalDate date) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_DATE,
                    new InvoiceMapper(),
                    Date.valueOf(date)
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoicesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_DATE_RANGE,
                    new InvoiceMapper(),
                    startDate,
                    endDate
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoicesByPaymentType(String paymentType) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_PAYMENT_TYPE,
                    new InvoiceMapper(),
                    paymentType
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoicesByOrderStatus(OrderStatus status) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_ORDER_STATUS,
                    new InvoiceMapper(),
                    status.toString()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Invoice> findInvoicesByPaidStatus(boolean isPaid) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_PAID_STATUS,
                    new InvoiceMapper(),
                    isPaid
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }
}
