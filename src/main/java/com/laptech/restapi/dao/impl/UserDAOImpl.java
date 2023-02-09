package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.mapper.UserMapper;
import com.laptech.restapi.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class UserDAOImpl implements UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "tbl_user";
    private final String INSERT = String.format("insert into %s values (0, ?, ?, ?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set name=?, gender=?, date_of_birth=?, phone=?, email=?, password=?, modified_date=now() where id=?", TABLE_NAME);
    private final String UPDATE_INFORMATION = String.format("update %s " +
            "set name=?, gender=?, date_of_birth=?, email=?, modified_date=now() where id=?", TABLE_NAME);
    private final String ENABLE_USER = String.format("update %s set is_active=true, modified_date=now() where id=?", TABLE_NAME);
    private final String DISABLE_USER = String.format("update %s set is_active=false, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE_USER = String.format("remove from %s where id=? and is_active=false", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "name=? and gender=? and date_of_birth=? and phone=? and email=?", TABLE_NAME);

    private final String QUERY_ONE_BY_PHONE = String.format("select * from %s where phone=? limit 1", TABLE_NAME);
    private final String QUERY_ONE_BY_REFRESH_TOKEN =
            String.format("select u.* from %s u, %s rf " +
                    "where rf.refresh_token=? and rf.expired_date < now() and u.id = rf.user_id",
                    TABLE_NAME, "tbl_refresh_token");

    private final String QUERY_USERS_BY_NAME = String.format("select * from %s where name like ?", TABLE_NAME);
    private final String QUERY_USERS_BY_GENDER = String.format("select * from %s where gender=?", TABLE_NAME);
    private final String QUERY_USERS_BY_ROLE = String.format("select * from %s where id in " +
            "(select ur.user_id from tbl_user_role ur, tbl_role r" +
            " where r.name=? and ur.role_id=r.id)", TABLE_NAME); // is fixing

    @Override
    public Long insert(User user) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setString(2, user.getGender().toString());
                ps.setDate(3, Date.valueOf(user.getDateOfBirth()));
                ps.setString(4, user.getPhone());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getPassword());
                ps.setBoolean(7, user.isActive());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error("[INSERT]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public int update(User user) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    user.getName(),
                    user.getGender().toString(),
                    user.getDateOfBirth(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getId()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE]: {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int updateInformation(UserDTO userDTO) {
        try {
            return jdbcTemplate.update(
                    UPDATE_INFORMATION,
                    userDTO.getName(),
                    userDTO.getGender().toString(),
                    userDTO.getDateOfBirth(),
                    userDTO.getEmail(),
                    userDTO.getId()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE INFO]: {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int enable(long userId) {
        try {
            return jdbcTemplate.update(
                    ENABLE_USER,
                    userId
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE ENABLE]: {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int disable(long userId) {
        try {
            return jdbcTemplate.update(
                    DISABLE_USER,
                    userId
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE DISABLE]: {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long userId) {
        User user = this.findById(userId);
        if (!user.isActive()) {
            try {
                return jdbcTemplate.update(
                        DELETE_USER,
                        userId
                );
            } catch (DataAccessException err) {
                log.error("[DELETE]: {}", err.getMessage());
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int count() {
        return this.findAll().size();
    }

    @Override
    public boolean isExists(User user) {
        try {
            User existsUser = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new UserMapper(),
                    user.getName(),
                    user.getGender().toString(),
                    user.getDateOfBirth(),
                    user.getPhone(),
                    user.getEmail()
            );
            return existsUser != null;
        } catch (DataAccessException err) {
            log.info("[CHECK EXIST]: {}", err.getMessage());
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new UserMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new UserMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public User findById(Long userId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new UserMapper(),
                    userId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ONE]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public User findUserByPhone(String phone) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_PHONE,
                    new UserMapper(),
                    phone
            );
        } catch (EmptyResultDataAccessException err) {
            log.info("[FIND BY PHONE]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public User findUserByRefreshToken(String refreshToken) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_REFRESH_TOKEN,
                    new UserMapper(),
                    refreshToken
            );
        } catch (EmptyResultDataAccessException err) {
            log.info("[FIND BY PHONE]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findUserByName(String name) {
        try {
            return jdbcTemplate.query(
                    QUERY_USERS_BY_NAME,
                    new UserMapper(),
                    "%" + name + "%"
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY NAME]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findUserByGender(Gender gender) {
        try {
            return jdbcTemplate.query(
                    QUERY_USERS_BY_GENDER,
                    new UserMapper(),
                    gender.toString()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY GENDER]: {}", err.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findUserByRole(String role) {
        try {
            return jdbcTemplate.query(
                    QUERY_USERS_BY_ROLE,
                    new UserMapper(),
                    role
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ROLE]: {}", err.getMessage());
            return null;
        }
    }
}

