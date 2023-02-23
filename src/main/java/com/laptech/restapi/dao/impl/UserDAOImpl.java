package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.mapper.UserMapper;
import com.laptech.restapi.model.User;
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
public class UserDAOImpl implements UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewUser}")
    private String INSERT;
    @Value("${sp_UpdateUser}")
    private String UPDATE;
    @Value("${sp_UpdateUserInformation}")
    private String UPDATE_INFORMATION;
    @Value("${sp_UpdateUserAccountStatus}")
    private String ENABLE_USER;
    @Value("${sp_UpdateUserAccountStatus}")
    private String DISABLE_USER;
    @Value("${sp_DeleteUser}")
    private String DELETE_USER;
    @Value("${sp_FindAllUsers}")
    private String QUERY_ALL;
    @Value("${}") // missing
    private String QUERY_LIMIT;
    @Value("${sp_FindUserById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindUserByPhone}")
    private String QUERY_ONE_BY_PHONE;
    @Value("${sp_FindUserByName}")
    private String QUERY_USERS_BY_NAME;
    @Value("${sp_FindUserByGender}")
    private String QUERY_USERS_BY_GENDER;
    @Value("${sp_FindUserByRoleName}")
    private String QUERY_USERS_BY_ROLE;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "name=? and gender=? and date_of_birth=? and phone=? and email=?", "tbl_user");
    private final String QUERY_ONE_BY_REFRESH_TOKEN =
            String.format("select u.* from %s u, %s rf " +
                            "where rf.refresh_token=? and rf.expired_date < now() and u.id = rf.user_id",
                            "tbl_user", "tbl_refresh_token");

    @Override
    public Long insert(User user) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    user.getName(),
                    user.getGender().toString(),
                    user.getDateOfBirth(),
                    user.getPhone(),
                    user.getEmail()
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getMessage());
            return null;
        }
    }

    @Override
    public int update(User user) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    user.getId(),
                    user.getName(),
                    user.getGender().toString(),
                    user.getDateOfBirth(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getPassword()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int updateInformation(UserDTO userDTO) {
        try {
            return jdbcTemplate.update(
                    UPDATE_INFORMATION,
                    userDTO.getId(),
                    userDTO.getName(),
                    userDTO.getGender().toString(),
                    userDTO.getDateOfBirth(),
                    userDTO.getEmail()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE INFO] {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int enable(long userId) {
        try {
            return jdbcTemplate.update(
                    ENABLE_USER,
                    userId,
                    true
            );
        } catch (DataAccessException err) {
            log.error("[ENABLE] {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int disable(long userId) {
        try {
            return jdbcTemplate.update(
                    DISABLE_USER,
                    userId,
                    false
            );
        } catch (DataAccessException err) {
            log.error("[DISABLE] {}", err.getMessage());
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
                log.error("[DELETE] {}", err.getMessage());
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
            log.info("[CHECK EXIST] {}", err.getMessage());
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
            log.warn("[FIND ALL] {}", err.getMessage());
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
            log.warn("[FIND LIMIT] {}", err.getMessage());
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
            log.warn("[FIND ONE] {}", err.getMessage());
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
            log.info("[FIND BY PHONE] {}", err.getMessage());
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
            log.info("[FIND BY REFRESH TOKEN] {}", err.getMessage());
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

