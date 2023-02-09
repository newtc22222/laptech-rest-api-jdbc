package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductUnitDAO;
import com.laptech.restapi.mapper.ProductUnitMapper;
import com.laptech.restapi.model.ProductUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    private final String TABLE_NAME = "tbl_product_unit";
    private final String INSERT = String.format("insert into %s values (?, ?, ?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    // Use with transform (cart -> invoice)
    private final String UPDATE = String.format("update %s " +
            "set cart_id=?, invoice_id=?, product_id=?, item_quantity=?, item_price=?, item_discount_price=?, modified_date=now() where id=?", TABLE_NAME);
    private final String UPDATE_PRODUCT_ITEM_PROPERTIES = String.format("update %s " +
            "set item_quantity=?, item_price=?, item_discount_price=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    //    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "cart_id=? and invoice_id=? and product_id=? and item_quantity=? and item_price=? and item_discount_price=?", TABLE_NAME);

    private final String QUERY_PRODUCT_ITEMS_BY_CART_ID =
            String.format("select * from %s where cart_id=?", TABLE_NAME);
    private final String QUERY_PRODUCT_ITEMS_BY_INVOICE_ID =
            String.format("select * from %s where invoice_id=?", TABLE_NAME);

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
                    unit.getDiscountPrice().doubleValue()
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
                    unit.getProductId(),
                    unit.getCartId(),
                    unit.getInvoiceId(),
                    unit.getQuantity(),
                    unit.getPrice().doubleValue(),
                    unit.getDiscountPrice().doubleValue(),
                    unit.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int updateProductUnitProperties(String itemId, int quantity, BigDecimal price, BigDecimal discountPrice) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PRODUCT_ITEM_PROPERTIES,
                    quantity,
                    price.doubleValue(),
                    discountPrice.doubleValue(),
                    itemId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(String itemId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    itemId
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
    public boolean isExists(ProductUnit unit) {
        try {
            ProductUnit existsUnit = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new ProductUnitMapper(),
                    unit.getProductId(),
                    unit.getCartId(),
                    unit.getInvoiceId(),
                    unit.getQuantity(),
                    unit.getPrice().doubleValue(),
                    unit.getDiscountPrice().doubleValue()
            );
            return existsUnit != null;
        } catch (DataAccessException err) {
            log.error(err);
            return false;
        }
    }

    @Override
    public List<ProductUnit> findAll() { // don't have any idea
        return null;
    }

    @Override
    public List<ProductUnit> findAll(long limit, long skip) { // same above
        return null;
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
    public List<ProductUnit> findProductUnitByCartId(String cartId) {
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
    public List<ProductUnit> findProductUnitByInvoiceId(String invoiceId) {
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
