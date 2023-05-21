package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dao.InvoiceDAO;
import com.laptech.restapi.dto.filter.InvoiceFilter;
import com.laptech.restapi.mapper.InvoiceMapper;
import com.laptech.restapi.model.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */

@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class InvoiceDAOImpl implements InvoiceDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewInvoice}")
    private String INSERT;
    @Value("${sp_UpdateInvoice}")
    private String UPDATE;
    @Value("${sp_UpdateInvoiceOrderStatus}")
    private String UPDATE_ORDER_STATUS;
    @Value("${sp_UpdateInvoicePaymentTypeAndPaidStatus}")
    private String UPDATE_PAYMENT_TYPE_AND_PAID_STATUS;
    @Value("${sp_DeleteInvoice}")
    private String DELETE;
    @Value("${sp_FindAllInvoices}")
    private String QUERY_ALL;
    @Value("${sp_FindInvoiceByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindInvoiceById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindInvoiceByUserId}")
    private String QUERY_INVOICES_BY_USER_ID;
    @Value("${sp_FindInvoiceByDate}")
    private String QUERY_INVOICES_BY_DATE;
    @Value("${sp_FindInvoiceByDateRange}")
    private String QUERY_INVOICES_BY_DATE_RANGE;
    @Value("${sp_FindInvoiceByOrderStatus}")
    private String QUERY_INVOICES_BY_ORDER_STATUS;

    @Value("${sp_CheckExistInvoice}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllInvoice}")
    private String COUNT_ALL;
    @Value("${sp_CountInvoiceWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public String insert(Invoice invoice) {
        try {
            jdbcTemplate.update(
                    INSERT,
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
                    invoice.getNote(),
                    invoice.getUpdateBy()
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
                    invoice.getNote(),
                    invoice.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updateOrderStatus(String invoiceId, OrderStatus status, String updateBy) {
        try {
            return jdbcTemplate.update(
                    UPDATE_ORDER_STATUS,
                    invoiceId,
                    status.toString(),
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE STATUS] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updatePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid, String updateBy) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PAYMENT_TYPE_AND_PAID_STATUS,
                    invoiceId,
                    paymentType,
                    isPaid,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE PAYMENT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String invoiceId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    invoiceId,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long count() {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_ALL,
                    Long.class
            );
            return Objects.requireNonNull(count);
        } catch (DataAccessException | NullPointerException err) {
            log.error("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(InvoiceFilter filter) {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_WITH_CONDITION,
                    Long.class,
                    filter.getObject(false)
            );
            return Objects.requireNonNull(count);
        } catch (DataAccessException | NullPointerException err) {
            log.error("[COUNT WITH CONDITION] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public boolean isExists(Invoice invoice) {
        try {
            Collection<Invoice> existsInvoice = jdbcTemplate.query(
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
            return existsInvoice.size() > 0;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Invoice> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new InvoiceMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Invoice> findWithFilter(InvoiceFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new InvoiceMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
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
        } catch (DataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Invoice> findInvoiceByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_USER_ID,
                    new InvoiceMapper(),
                    userId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY USER ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Invoice> findInvoiceByDate(LocalDate date) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_DATE,
                    new InvoiceMapper(),
                    Date.valueOf(date)
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY DATE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Invoice> findInvoiceByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_DATE_RANGE,
                    new InvoiceMapper(),
                    startDate,
                    endDate
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY DATE RANGE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Invoice> findInvoiceByOrderStatus(OrderStatus status) {
        try {
            return jdbcTemplate.query(
                    QUERY_INVOICES_BY_ORDER_STATUS,
                    new InvoiceMapper(),
                    status.toString()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY ORDER STATUS] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
