package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.CommentDAO;
import com.laptech.restapi.dto.filter.CommentFilter;
import com.laptech.restapi.mapper.CommentMapper;
import com.laptech.restapi.model.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */

@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class CommentDAOImpl implements CommentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewComment}")
    private String INSERT;
    @Value("${sp_UpdateComment}")
    private String UPDATE;
    @Value("${sp_DeleteComment}")
    private String DELETE;

    @Value("${sp_FindAllComments}") // add order by created_date desc
    private String QUERY_ALL;
    @Value("${sp_FindCommentByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindCommentById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindCommentByProductId}")
    private String QUERY_ALL_COMMENTS_OF_PRODUCT;
    @Value("${sp_FindCommentByUserPhone}")
    private String QUERY_ALL_COMMENTS_OF_USER;
    @Value("${sp_FindCommentByRootCommentId}")
    private String QUERY_COMMENT_BY_ROOT_COMMENT_ID; // not use
    @Value("${sp_CheckExistComment}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllComment}")
    private String COUNT_ALL;
    @Value("${sp_CountCommentWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public String insert(Comment comment) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    comment.getId(),
                    comment.getRootCommentId(),
                    comment.getProductId(),
                    comment.getUsername(),
                    comment.getPhone(),
                    comment.getContent(),
                    comment.getUpdateBy()
            );
            return comment.getId();
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Comment comment) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    comment.getId(),
                    comment.getRootCommentId(),
                    comment.getProductId(),
                    comment.getUsername(),
                    comment.getPhone(),
                    comment.getContent(),
                    comment.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String commentId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    commentId,
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
    public long countWithFilter(CommentFilter filter) {
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
    public boolean isExists(Comment comment) {
        try {
            Collection<Comment> existsComment = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new CommentMapper(),
                    comment.getRootCommentId(),
                    comment.getProductId(),
                    comment.getUsername(),
                    comment.getPhone(),
                    comment.getContent()
            );
            return existsComment.size() > 0;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Comment> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new CommentMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Comment> findWithFilter(CommentFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new CommentMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
            log.warn("[FIND FILTER] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Comment> findCommentByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_COMMENTS_OF_PRODUCT,
                    new CommentMapper(),
                    productId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY PRODUCT ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Comment> findCommentByUserPhone(String phone) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_COMMENTS_OF_USER,
                    new CommentMapper(),
                    phone
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY USER PHONE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Comment findById(String commentId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new CommentMapper(),
                    commentId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
