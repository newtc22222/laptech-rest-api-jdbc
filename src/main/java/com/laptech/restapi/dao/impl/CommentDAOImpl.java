package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.CommentDAO;
import com.laptech.restapi.mapper.CommentMapper;
import com.laptech.restapi.model.Comment;
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
 * @since 2022-11-21
 */

@Transactional
@Log4j2
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
    @Value("${sp_FindAllCommentsLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindCommentById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindCommentByProductId}")
    private String QUERY_ALL_COMMENTS_OF_PRODUCT;
    @Value("${sp_FindCommentByUserPhone}")
    private String QUERY_ALL_COMMENTS_OF_USER;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "root_comment_id=? and product_id=? and username=? and phone=? and content=?", "tbl_comment");

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
                    comment.getContent()
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
                    comment.getContent()
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
                    commentId
            );
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long count() {
        return this.findAll().size();
    }

    @Override
    public boolean isExists(Comment comment) {
        try {
            Comment existsComment = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new CommentMapper(),
                    comment.getRootCommentId(),
                    comment.getProductId(),
                    comment.getUsername(),
                    comment.getPhone(),
                    comment.getContent()
            );
            return existsComment != null;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Comment> findCommentByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_COMMENTS_OF_PRODUCT,
                    new CommentMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY PRODUCT ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Comment> findCommentByUserPhone(String phone) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_COMMENTS_OF_USER,
                    new CommentMapper(),
                    phone
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY USER PHONE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Comment> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new CommentMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Comment> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new CommentMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
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
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
