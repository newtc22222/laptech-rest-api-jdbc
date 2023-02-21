package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ImportProductDAO;
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
import java.util.List;

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
    @Value("${sp_FindAllImportsLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindImportById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindImportByProductId}")
    private String QUERY_IMPORT_PRODUCT_TICKETS_BY_PRODUCT_ID;
    @Value("${sp_FindImportProductByDate}")
    private String QUERY_IMPORT_PRODUCT_TICKETS_BY_DATE;
    @Value("${sp_FindImportProductByDateRange}")
    private String QUERY_IMPORT_PRODUCT_TICKETS_BY_DATE_RANGE;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "product_id=? and quantity=? and imported_price=? and imported_date=?", "tbl_import");

    @Override
    public String insert(ImportProduct ticket) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    ticket.getId(),
                    ticket.getProductId(),
                    ticket.getQuantity(),
                    ticket.getImportedPrice().doubleValue(),
                    Timestamp.valueOf(ticket.getImportedDate())
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
                    Timestamp.valueOf(ticket.getImportedDate())
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String ticketId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    ticketId
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
    public List<ImportProduct> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ImportProductMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<ImportProduct> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new ImportProductMapper(),
                    limit,
                    skip
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
    public List<ImportProduct> findImportProductByProductId(String productId) {
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
    public List<ImportProduct> findImportProductByDate(LocalDate date) {
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
    public List<ImportProduct> findImportProductByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
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
