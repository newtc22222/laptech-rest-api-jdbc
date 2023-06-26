package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ResetPasswordDAO;
import com.laptech.restapi.mapper.ResetPasswordMapper;
import com.laptech.restapi.mapper.UserMapper;
import com.laptech.restapi.model.ResetPassword;
import com.laptech.restapi.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:query.properties")
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordDAOImpl implements ResetPasswordDAO {
    private final JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertResetPasswordToken}")
    private String INSERT;

    @Value("${sp_FindResetPasswordTokenByToken}")
    private String FIND_RESET_PASSWORD_TOKEN_BY_TOKEN;

    @Value("@{sp_FindUserByResetPasswordToken}")
    private String FIND_USER_BY_TOKEN;

    @Override
    public int insert(ResetPassword resetPassword) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    resetPassword.getUserId(),
                    resetPassword.getToken(),
                    resetPassword.getExpiredTime()
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public ResetPassword findResetPasswordTokenByToken(String token) {
        try {
            return jdbcTemplate.queryForObject(
                    FIND_RESET_PASSWORD_TOKEN_BY_TOKEN,
                    new ResetPasswordMapper(),
                    token
            );
        } catch (DataAccessException err) {
            log.info("[FIND TOKEN] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public User findUserByResetPasswordToken(String token) {
        try {
            return jdbcTemplate.queryForObject(
                    FIND_USER_BY_TOKEN,
                    new UserMapper(),
                    token
            );
        } catch (DataAccessException err) {
            log.info("[FIND USER] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
