package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.CommentDAO;
import com.laptech.restapi.mapper.CommentMapper;
import com.laptech.restapi.model.Comment;
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
 * @since 2022-11-21
 */

@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class CommentDAOImpl implements CommentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Query String
    private final String TABLE_NAME = "tbl_comment";
    private final String INSERT = String.format("insert into %s values (?, ?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set root_comment_id=?, product_id=?, username=?, phone=?, content=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    //    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_ALL_COMMENTS_OF_PRODUCT = String.format("select * from %s where product_id=?", TABLE_NAME);
    private final String QUERY_ALL_COMMENTS_OF_USER = String.format("select * from %s where phone=?", TABLE_NAME);
    private final String QUERY_ALL = String.format("select * from %s order by created_date desc", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s order by created_date desc limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=?", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "root_comment_id=? and product_id=? and username=? and phone=? and content=?", TABLE_NAME);

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
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Comment comment) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    comment.getRootCommentId(),
                    comment.getProductId(),
                    comment.getUsername(),
                    comment.getPhone(),
                    comment.getContent(),
                    comment.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(String commentId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    commentId
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
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }
}
