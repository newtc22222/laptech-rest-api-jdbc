package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dto.filter.ProductFilter;
import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.mapper.ProductMapper;
import com.laptech.restapi.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class ProductDAOImpl implements ProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewProduct}")
    private String INSERT;
    @Value("${sp_UpdateProduct}")
    private String UPDATE_ALL;
    @Value("${sp_UpdateProductPrice}")
    private String UPDATE_PRICE;
    @Value("${sp_DeleteProduct}")
    private String DELETE;

    @Value("${sp_FindAllProducts}")
    private String QUERY_ALL;
    @Value("${sp_FindProductByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindProductById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindAccessoryByProductId}")
    private String QUERY_ACCESSORIES;
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
    @Value("${sp_CheckExistProduct}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllProduct}")
    private String COUNT_ALL;
    @Value("${sp_CountProductWithCondition}")
    private String COUNT_WITH_CONDITION;

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
                    product.getDescriptionDetail(),
                    product.getUpdateBy()
            );
            return product.getId();
        } catch (DataAccessException err) {
            log.error("[INSERT NEW PRODUCT]: {}", err.getLocalizedMessage());
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
                    product.getDescriptionDetail(),
                    product.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE PRODUCT]: {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updatePrice(ProductPriceDTO productDTO, String updateBy) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PRICE,
                    productDTO.getListedPrice(),
                    productDTO.getId(),
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE PRICE]: {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String productId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    productId,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[DELETE PRODUCT]: {}", err.getLocalizedMessage());
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
    public long countWithFilter(ProductFilter filter) {
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
    public boolean isExists(Product product) {
        try {
            Collection<Product> existsProduct = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new ProductMapper(),
                    product.getBrandId(),
                    product.getCategoryId(),
                    product.getName(),
                    Date.valueOf(product.getReleasedDate()),
                    product.getQuantityInStock(),
                    product.getListedPrice()
            );
            return existsProduct.size() > 0;
        } catch (DataAccessException err) {
            log.error("[CHECK EXISTED]: {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Product> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ProductMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND ALL]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Product> findWithFilter(ProductFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new ProductMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
            log.warn("[FIND WITH FILTER]: {}", err.getLocalizedMessage());
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
        } catch (DataAccessException err) {
            log.warn("[FIND BY ID]: {}", err.getLocalizedMessage());
            return null;
        }
    }


    @Override
    public Collection<Product> findAccessoryByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ACCESSORIES,
                    new ProductMapper(),
                    productId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND ACCESSORIES OF PRODUCT]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Product> findProductByBrandId(long brandId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_BRAND_ID,
                    new ProductMapper(),
                    brandId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND PRODUCT BY BRAND ID]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Product> findProductByCategoryId(long categoryId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_CATEGORY_ID,
                    new ProductMapper(),
                    categoryId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND PRODUCT BY CATEGORY ID]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Product> findProductByReleasedYear(int year) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_RELEASED_YEAR,
                    new ProductMapper(),
                    year
            );
        } catch (DataAccessException err) {
            log.warn("[FIND PRODUCT BY RELEASED YEAR]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Product> findProductByPriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_PRICE_RANGE,
                    new ProductMapper(),
                    startPrice.doubleValue(),
                    endPrice.doubleValue()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND PRODUCT BY PRICE RANGE]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Product> findProductByLabelId(long labelId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCTS_BY_LABEL,
                    new ProductMapper(),
                    labelId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND PRODUCT BY LABEL ID]: {}", err.getLocalizedMessage());
            return null;
        }
    }
}

