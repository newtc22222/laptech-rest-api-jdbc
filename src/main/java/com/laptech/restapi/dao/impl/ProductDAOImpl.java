package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.mapper.ProductMapper;
import com.laptech.restapi.model.Product;
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

    @Value("${sp_InsertNewProduct}")
    private String INSERT;
    @Value("${sp_UpdateProduct}")
    private String UPDATE_ALL;
    @Value("${}") // missing
    private String UPDATE_PRICE;

    @Value("${sp_DeleteProduct}")
    private String DELETE;
    @Value("${sp_FindAllProducts}")
    private String QUERY_ALL;
    @Value("${}") // missing
    private String QUERY_LIMIT;
    @Value("${sp_FindProductById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindAccessoryByProductId}")
    private String QUERY_ACCESSORIES;

    @Value("${sp_FindProductByName}")
    private String QUERY_PRODUCTS_BY_NAME;
    @Value("${sp_FindProductByBrandId}")
    private String QUERY_PRODUCTS_BY_BRAND_ID;
    @Value("${sp_FindProductByCategoryId}")
    private String QUERY_PRODUCTS_BY_CATEGORY_ID;
    @Value("${sp_FindProductByReleasedYear}")
    private String QUERY_PRODUCTS_BY_RELEASED_YEAR;
    @Value("${sp_FindProductByPriceRange}")
    private String QUERY_PRODUCTS_BY_PRICE_RANGE;
    @Value("${sp_FindProductByLabelId}")
    private String QUERY_PRODUCTS_BY_LABEL;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "brand_id=? and category_id=? and name=? and released_date=? and quantity_in_stock=? and listed_price=? and " +
            "specifications=? and description_detail=?", "tbl_product");

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
    public int delete(String productId, String updateBy) {
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
    public long count() {
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

