package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.dao.ProductImageDAO;
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

import java.util.List;

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
    @Value("${sp_UpdateProductImagePathAndType}")
    private String UPDATE_PATH_AND_TYPE;
    @Value("${sp_DeleteProductImage}")
    private String DELETE;
    @Value("${sp_FindAllProductImages}")
    private String QUERY_ALL;
    @Value("${}") // missing
    private String QUERY_LIMIT;
    @Value("${sp_FindProductImageById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindProductImageByProductId}")
    private String QUERY_PRODUCT_IMAGES_BY_PRODUCT_ID;
    @Value("${sp_FindProductImageByImageType}")
    private String QUERY_PRODUCT_IMAGES_BY_IMAGE_TYPE;
    @Value("${sp_FindProductImageByProductIdAndImageType}")
    private String QUERY_PRODUCT_IMAGES_BY_PRODUCT_ID_AND_IMAGE_TYPE;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "product_id=? and feedback_id=? and url=? and type=?", "tbl_product_image");

    @Override
    public String insert(ProductImage image) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    image.getId(),
                    image.getProductId(),
                    image.getFeedbackId(),
                    image.getUrl(),
                    image.getType().toString()
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
                    image.getType().toString()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updatePathAndType(String imageId, String path, ImageType type) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PATH_AND_TYPE,
                    imageId,
                    path,
                    type.toString()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE PATH AND TYPE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String imageId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    imageId
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
    public boolean isExists(ProductImage image) {
        try {

            ProductImage existsImage = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new ProductImageMapper(),
                    image.getProductId(),
                    image.getFeedbackId(),
                    image.getUrl(),
                    image.getType().toString()
            );
            return existsImage != null;
        } catch (DataAccessException err) {
            log.error("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<ProductImage> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new ProductImageMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<ProductImage> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new ProductImageMapper(),
                    limit,
                    skip
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
    public List<ProductImage> findProductImageByProductId(String productId) {
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
    public List<ProductImage> findProductImageByImageType(ImageType type) {
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

    @Override
    public List<ProductImage> findProductImageByProductIdAndImageType(String productId, ImageType type) {
        try {
            return jdbcTemplate.query(
                    QUERY_PRODUCT_IMAGES_BY_PRODUCT_ID_AND_IMAGE_TYPE,
                    new ProductImageMapper(),
                    productId,
                    type.toString()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY PRODUCT ID AND IMAGE TYPE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}

