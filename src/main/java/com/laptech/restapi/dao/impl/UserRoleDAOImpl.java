package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.UserRoleDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
@Transactional
@Log4j2
@Component
@PropertySource("query.properties")
public class UserRoleDAOImpl implements UserRoleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "joshua_tbl_product_hard_drive"; // tbl_user_role
    private final String INSERT = String.format("insert into %s values (?, ?)", TABLE_NAME);
    private final String REMOVE = String.format("delete from %s where user_id=? and role_id=?", TABLE_NAME);

    @Override
    public int insert(Long userId, Integer roleId) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    userId,
                    roleId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int remove(Long userId, Integer roleId) {
        try {
            return jdbcTemplate.update(
                    REMOVE,
                    userId,
                    roleId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }
}
