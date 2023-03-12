package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.dao.ProductImageDAO;
import com.laptech.restapi.dto.filter.ProductImageFilter;
import com.laptech.restapi.mapper.ProductImageMapper;
import com.laptech.restapi.model.ProductImage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class ProductImageDAOImpl implements ProductImageDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewProductImage}")
    private String INSERT;
    @Value("${sp_UpdateProductImage}")
    private String UPDATE;
    @Value("${sp_UpdateProductImageUrlAndType}")
    private String UPDATE_URL_AND_TYPE;
    @Value("${sp_DeleteProductImage}")
    private String DELETE;
    @Value("${sp_FindAllProductImages}")
    private String QUERY_ALL;
    @Value("${sp_FindProductImageByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindProductImageById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindProductImageByProductId}")
    private String QUERY_PRODUCT_IMAGES_BY_PRODUCT_ID;
    @Value("${sp_FindProductImageByImageType}")
    private String QUERY_PRODUCT_IMAGES_BY_IMAGE_TYPE;
    @Value("${sp_CheckExistProductImage}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllProductImage}")
    private String COUNT_ALL;
    @Value("${sp_CountProductImageWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public String insert(ProductImage image) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    image.getId(),
                    image.getProductId(),
                    image.getFeedbackId(),
                    image.getUrl(),
                    image.getType().toString(),
                    image.getUpdateBy()
            );
            return image.getId();
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(ProductImage image) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    image.getId(),
                    image.getProductId(),
                    image.getFeedbackId(),
                    image.getUrl(),
                    image.getType().toString(),
                    image.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updateUrlAndType(String imageId, String url, ImageType type, String updateBy) {
        try {
            return jdbcTemplate.update(
                    UPDATE_URL_AND_TYPE,
                    imageId,
                    url,
                    type.toString(),
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE PATH AND TYPE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String imageId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    imageId,
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
            log.error("[COUNT WITH CONDITION] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(ProductImageFilter filter) {
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
    public boolean isExists(ProductImage image) {
        try {
            Collection<ProductImage> existsImage = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new ProductImageMapper(),
                    image.getProductId(),
                    image.getFeedbackId(),
                    image.getUrl(),
                    image.getType().toString()
            );
            return existsImage.size() > 0;
        } catch (DataAccessException err) {
            log.error("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<ProductImage> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ProductImageMapper(),
                    pagingOption.getObject()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ProductImage> findWithFilter(ProductImageFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new ProductImageMapper(),
                    filter.getObject(true)
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND LIMIT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ProductImage findById(String imageId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new ProductImageMapper(),
                    imageId
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ProductImage> findProductImageByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCT_IMAGES_BY_PRODUCT_ID,
                    new ProductImageMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY PRODUCT ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<ProductImage> findProductImageByImageType(ImageType type) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCT_IMAGES_BY_IMAGE_TYPE,
                    new ProductImageMapper(),
                    type.toString()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY IMAGE TYPE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}

