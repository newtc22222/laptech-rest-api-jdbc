package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.dao.ProductImageDAO;
import com.laptech.restapi.mapper.ProductImageMapper;
import com.laptech.restapi.model.ProductImage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final String TABLE_NAME = "tbl_product_image";
    private final String INSERT = String.format("insert into %s values (?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set feedback_id=?, url=?, type=?, modified_date=now() where id=? and product_id=?", TABLE_NAME);
    private final String UPDATE_PATH_AND_TYPE = String.format("update %s set url=?, type=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "product_id=? and feedback_id=? and url=? and type=?", TABLE_NAME);

    private final String QUERY_PRODUCT_IMAGES_BY_PRODUCT_ID =
            String.format("select * from %s where product_id=?", TABLE_NAME);
    private final String QUERY_PRODUCT_IMAGES_BY_IMAGE_TYPE =
            String.format("select * from %s where type=?", TABLE_NAME);
    private final String QUERY_PRODUCT_IMAGES_BY_PRODUCT_ID_AND_IMAGE_TYPE =
            String.format("select * from %s where product_id=? and type=?", TABLE_NAME);

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
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(ProductImage image) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    image.getFeedbackId(),
                    image.getUrl(),
                    image.getType().toString(),
                    image.getId(),
                    image.getProductId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int updatePathAndType(String imageId, String path, ImageType type) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PATH_AND_TYPE,
                    path,
                    type.toString(),
                    imageId
            );
        } catch (DataAccessException err) {
            log.error(err);
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
            log.error(err);
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
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }
}

