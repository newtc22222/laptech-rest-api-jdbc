package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.CartDAO;
import com.laptech.restapi.mapper.CartMapper;
import com.laptech.restapi.model.Cart;
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
public class CartDAOImpl implements CartDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Query String
    private final String TABLE_NAME = "tbl_cart";
    private final String INSERT = String.format("insert into %s values (?, ?, ?)", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set discount_id=? where id=? and user_id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=?", TABLE_NAME);
    private final String QUERY_CHECK_EXISTS = String.format("select * from %s where id=? and user_id=?", TABLE_NAME);
    private final String QUERY_ONE_BY_USER_ID = String.format("select * from %s where user_id=?", TABLE_NAME);

    @Override
    public String insert(Cart cart) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    cart.getId(),
                    cart.getUserId(),
                    cart.getDiscountId()
            );
            return cart.getId();
        } catch (DataAccessException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Cart cart) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    cart.getDiscountId(),
                    cart.getId(),
                    cart.getUserId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(String id) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    id
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean isExists(Cart cart) {
        try {
            Cart existsCart = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXISTS,
                    new CartMapper(),
                    cart.getId(),
                    cart.getUserId()
            );
            return existsCart != null;
        } catch (DataAccessException err) {
            log.error(err);
            return false;
        }
    }

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public List<Cart> findAll(long limit, long skip) {
        return null;
    }

    @Override
    public Cart findById(String cartId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new CartMapper(),
                    cartId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public Cart findCartByUserId(long userId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_USER_ID,
                    new CartMapper(),
                    userId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }
}
