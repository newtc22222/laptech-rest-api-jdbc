package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.ImportProductDAO;
import com.laptech.restapi.dto.filter.ImportProductFilter;
import com.laptech.restapi.mapper.ImportProductMapper;
import com.laptech.restapi.model.ImportProduct;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */

@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class ImportProductDAOImpl implements ImportProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewImport}")
    private String INSERT;
    @Value("${sp_UpdateImport}")
    private String UPDATE;
    @Value("${sp_DeleteImport}")
    private String DELETE;
    @Value("${sp_FindAllImports}")
    private String QUERY_ALL;
    @Value("${sp_FindImportByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindImportById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindImportByProductId}")
    private String QUERY_IMPORT_PRODUCT_TICKETS_BY_PRODUCT_ID;
    @Value("${sp_FindImportByDate}")
    private String QUERY_IMPORT_PRODUCT_TICKETS_BY_DATE;
    @Value("${sp_FindImportByDateRange}")
    private String QUERY_IMPORT_PRODUCT_TICKETS_BY_DATE_RANGE;
    @Value("${sp_CheckExistImport}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllImport}")
    private String COUNT_ALL;
    @Value("${sp_CountImportWithCondition}")
    private String COUNT_WITH_CONDITION;


    @Override
    public String insert(ImportProduct ticket) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    ticket.getId(),
                    ticket.getProductId(),
                    ticket.getQuantity(),
                    ticket.getImportedPrice().doubleValue(),
                    Timestamp.valueOf(ticket.getImportedDate()),
                    ticket.getUpdateBy()
            );
            return ticket.getId();
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(ImportProduct ticket) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    ticket.getId(),
                    ticket.getProductId(),
                    ticket.getQuantity(),
                    ticket.getImportedPrice().doubleValue(),
                    Timestamp.valueOf(ticket.getImportedDate()),
                    ticket.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String ticketId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    ticketId,
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
    public long countWithFilter(ImportProductFilter filter) {
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
    public boolean isExists(ImportProduct ticket) {
        try {

            ImportProduct existsImport = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new ImportProductMapper(),
                    ticket.getProductId(),
                    ticket.getQuantity(),
                    ticket.getImportedPrice().doubleValue(),
                    Timestamp.valueOf(ticket.getImportedDate())
            );
            return existsImport != null;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<ImportProduct> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ImportProductMapper(),
                    pagingOption.getObject()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ImportProduct> findWithFilter(ImportProductFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new ImportProductMapper(),
                    filter.getObject(true)
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ImportProduct findById(String ticketId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new ImportProductMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ImportProduct> findImportProductByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_IMPORT_PRODUCT_TICKETS_BY_PRODUCT_ID,
                    new ImportProductMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY PRODUCT ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ImportProduct> findImportProductByDate(LocalDate date) {
        try {
            return jdbcTemplate.query(
                    QUERY_IMPORT_PRODUCT_TICKETS_BY_DATE,
                    new ImportProductMapper(),
                    Date.valueOf(date)
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY DATE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ImportProduct> findImportProductByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return jdbcTemplate.query(
                    QUERY_IMPORT_PRODUCT_TICKETS_BY_DATE_RANGE,
                    new ImportProductMapper(),
                    startDate,
                    endDate
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY DATE RANGE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
