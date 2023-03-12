package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.ProductUnitDAO;
import com.laptech.restapi.dto.filter.ProductUnitFilter;
import com.laptech.restapi.mapper.ProductUnitMapper;
import com.laptech.restapi.model.ProductUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class ProductUnitDAOImpl implements ProductUnitDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewProductUnit}")
    private String INSERT;
    @Value("${sp_UpdateProductUnit}")
    private String UPDATE;
    @Value("${sp_UpdateProductUnitProperties}")
    private String UPDATE_PRODUCT_ITEM_PROPERTIES;
    @Value("${sp_DeleteProductUnit}")
    private String DELETE;

    @Value("${sp_FindAllProductUnits}")
    private String QUERY_ALL;
    @Value("${sp_FindProductUnitByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindProductUnitById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindProductUnitByCartId}")
    private String QUERY_PRODUCT_ITEMS_BY_CART_ID;
    @Value("${sp_FindProductUnitByInvoiceId}")
    private String QUERY_PRODUCT_ITEMS_BY_INVOICE_ID;
    @Value("${sp_CheckExistProductUnit}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllProductUnit}")
    private String COUNT_ALL;
    @Value("${sp_CountProductUnitWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public String insert(ProductUnit unit) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    unit.getId(),
                    unit.getProductId(),
                    unit.getCartId(),
                    unit.getInvoiceId(),
                    unit.getQuantity(),
                    unit.getPrice().doubleValue(),
                    unit.getDiscountPrice().doubleValue(),
                    unit.getUpdateBy()
            );
            return unit.getId();
        } catch (DataAccessException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(ProductUnit unit) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    unit.getId(),
                    unit.getProductId(),
                    unit.getCartId(),
                    unit.getInvoiceId(),
                    unit.getQuantity(),
                    unit.getPrice().doubleValue(),
                    unit.getDiscountPrice().doubleValue(),
                    unit.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int updateProductUnitProperties(String itemId, int quantity, BigDecimal price, BigDecimal discountPrice, String updateBy) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PRODUCT_ITEM_PROPERTIES,
                    itemId,
                    quantity,
                    price.doubleValue(),
                    discountPrice.doubleValue(),
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(String itemId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    itemId,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error(err);
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
    public long countWithFilter(ProductUnitFilter filter) {
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
    public boolean isExists(ProductUnit unit) {
        try {
            Collection<ProductUnit> existsUnit = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new ProductUnitMapper(),
                    unit.getProductId(),
                    unit.getCartId(),
                    unit.getInvoiceId(),
                    unit.getQuantity(),
                    unit.getPrice().doubleValue(),
                    unit.getDiscountPrice().doubleValue()
            );
            return existsUnit.size() > 0;
        } catch (DataAccessException err) {
            log.error(err);
            return false;
        }
    }

    @Override
    public Collection<ProductUnit> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ProductUnitMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ProductUnit> findWithFilter(ProductUnitFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ProductUnitMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ProductUnit findById(String itemId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new ProductUnitMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public Collection<ProductUnit> findProductUnitByCartId(String cartId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCT_ITEMS_BY_CART_ID,
                    new ProductUnitMapper(),
                    cartId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public Collection<ProductUnit> findProductUnitByInvoiceId(String invoiceId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCT_ITEMS_BY_INVOICE_ID,
                    new ProductUnitMapper(),
                    invoiceId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }
}
