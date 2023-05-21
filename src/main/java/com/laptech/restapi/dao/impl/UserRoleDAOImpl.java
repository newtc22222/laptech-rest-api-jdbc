package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.UserRoleDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class UserRoleDAOImpl implements UserRoleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertUserRole}")
    private String INSERT;
    @Value("${sp_DeleteUserRole}")
    private String REMOVE;

    @Override
    public int insert(Long userId, Integer roleId) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    userId,
                    roleId
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
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
            log.error("[REMOVE] {}", err.getLocalizedMessage());
            return 0;
        }
    }
}
