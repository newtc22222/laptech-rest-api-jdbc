package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.mapper.ProductMapper;
import com.laptech.restapi.model.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class ProductDAOImpl implements ProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Query String
    private final String TABLE_NAME = "tbl_product";
    private final String INSERT = String.format("insert into %s values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE_ALL = String.format("update %s " +
            "set brand_id=?, category_id=?, name=?, released_date=?, quantity_in_stock=?, listed_price=?, " +
            "specifications=?, description_detail=?, modified_date=now() " +
            "where id=?", TABLE_NAME);
    private final String UPDATE_PRICE = String.format("update %s set listed_price=?, modified_date=now() where id=?", TABLE_NAME);
    //    private final String UPDATE_PROPERTIES = String.format("update %s " +
//            "set name=?, brand_id=?, released_date=?, specifications=?, description_detail=?, " +
//            "modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=?", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "brand_id=? and category_id=? and name=? and released_date=? and quantity_in_stock=? and listed_price=? and " +
            "specifications=? and description_detail=?", TABLE_NAME);
    private final String QUERY_ACCESSORIES = String.format("select * from %s " +
            "where id in (select accessory_id from %s where product_id=?)", TABLE_NAME, "tbl_product_accessory");

    private final String QUERY_PRODUCTS_BY_NAME =
            String.format("select * from %s where name like ?", TABLE_NAME);
    private final String QUERY_PRODUCTS_BY_BRAND_ID =
            String.format("select * from %s where brand_id=?", TABLE_NAME);
    private final String QUERY_PRODUCTS_BY_CATEGORY_ID =
            String.format("select * from %s where category_id=?", TABLE_NAME);
    private final String QUERY_PRODUCTS_BY_RELEASED_YEAR =
            String.format("select * from %s where year(released_date)=?", TABLE_NAME);
    private final String QUERY_PRODUCTS_BY_PRICE_RANGE =
            String.format("select * from %s where price between ? and ?", TABLE_NAME);
    private final String QUERY_PRODUCTS_BY_LABEL =
            String.format("select * from %s where id in (select product_id from %s where label_id=?)",
                    TABLE_NAME, "joshua_tbl_product_graphic_card"); // joshua_tbl_product_label

    @Override
    public String insert(Product product) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    product.getId(),
                    product.getBrandId(),
                    product.getCategoryId(),
                    product.getName(),
                    Date.valueOf(product.getReleasedDate()),
                    product.getQuantityInStock(),
                    product.getListedPrice(),
                    product.getSpecifications(),
                    product.getDescriptionDetail()
            );
            return product.getId();
        } catch (DataAccessException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Product product) {
        try {
            return jdbcTemplate.update(
                    UPDATE_ALL,
                    product.getBrandId(),
                    product.getCategoryId(),
                    product.getName(),
                    Date.valueOf(product.getReleasedDate()),
                    product.getQuantityInStock(),
                    product.getListedPrice(),
                    product.getSpecifications(),
                    product.getDescriptionDetail(),
                    product.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int updatePrice(ProductPriceDTO productDTO) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PRICE,
                    productDTO.getListed_price(),
                    productDTO.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(String productId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    productId
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
    public boolean isExists(Product product) {
        try {

            Product existsProduct = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new ProductMapper(),
                    product.getBrandId(),
                    product.getCategoryId(),
                    product.getName(),
                    Date.valueOf(product.getReleasedDate()),
                    product.getQuantityInStock(),
                    product.getListedPrice(),
                    product.getSpecifications(),
                    product.getDescriptionDetail()
            );
            return existsProduct != null;
        } catch (DataAccessException err) {
            log.error(err);
            return false;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ProductMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Product> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new ProductMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public Product findById(String productId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new ProductMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }


    @Override
    public List<Product> findAccessoryByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ACCESSORIES,
                    new ProductMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Product> findProductByName(String name) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_NAME,
                    new ProductMapper(),
                    "%" + name + "%"
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Product> findProductByBrandId(long brandId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_BRAND_ID,
                    new ProductMapper(),
                    brandId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Product> findProductByCategoryId(long categoryId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_CATEGORY_ID,
                    new ProductMapper(),
                    categoryId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Product> findProductByReleasedYear(int year) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_RELEASED_YEAR,
                    new ProductMapper(),
                    year
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Product> findProductByPriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_PRICE_RANGE,
                    new ProductMapper(),
                    startPrice.doubleValue(),
                    endPrice.doubleValue()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Product> findProductByLabel(long labelId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_LABEL,
                    new ProductMapper(),
                    labelId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }
}

