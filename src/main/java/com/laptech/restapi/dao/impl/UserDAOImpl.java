package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dto.filter.UserFilter;
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

import java.util.Collection;
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

    @Value("${sp_InsertNewUser}")
    private String INSERT;
    @Value("${sp_UpdateUser}")
    private String UPDATE;
    @Value("${sp_UpdateUserInformation}")
    private String UPDATE_INFORMATION;
    @Value("${sp_UpdateUserPassword}")
    private String UPDATE_PASSWORD;
    @Value("${sp_UpdateUserAccountStatus}")
    private String CHANGE_USER_ACCOUNT_STATUS;
    @Value("${sp_DeleteUser}")
    private String DELETE_USER;
    @Value("${sp_FindAllUsers}")
    private String QUERY_ALL;
    @Value("${sp_FindUserByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindUserById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindUserByPhone}")
    private String QUERY_ONE_BY_PHONE;
    @Value("${sp_FindUserByRoleName}")
    private String QUERY_USER_BY_ROLE;

    @Value("${sp_CheckExistUser}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllUser}")
    private String COUNT_ALL;
    @Value("${sp_CountUserWithCondition}")
    private String COUNT_WITH_CONDITION;

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
                    user.getEmail(),
                    user.getPassword(),
                    user.getUpdateBy()
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
                    user.getPassword(),
                    user.getUpdateBy()
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
                    userDTO.getEmail(),
                    userDTO.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE INFO] {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int updatePassword(User user) {
        try {
            return jdbcTemplate.update(
                    UPDATE_PASSWORD,
                    user.getId(),
                    user.getPassword(),
                    user.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE PASSWORD] {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int enable(long userId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    CHANGE_USER_ACCOUNT_STATUS,
                    userId,
                    true,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[ENABLE] {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int disable(long userId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    CHANGE_USER_ACCOUNT_STATUS,
                    userId,
                    false,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[DISABLE] {}", err.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long userId, String updateBy) {
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
    public long count() {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_WITH_CONDITION,
                    Long.class
            );
            return Objects.requireNonNull(count);
        } catch (DataAccessException | NullPointerException err) {
            log.error("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(UserFilter filter) {
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
    public Collection<User> findAll(PagingOptionDTO pagingOption) {
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
    public Collection<User> findWithFilter(UserFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new UserMapper(),
                    filter.getObject(true)
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND FILTER] {}", err.getMessage());
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
        String query = String.format("select u.* " +
                "from %s u, %s rf " +
                "where rf.refresh_token=? " +
                "and rf.expired_date < now() " +
                "and u.id = rf.user_id", "tbl_user", "tbl_refresh_token");
        try {
            return jdbcTemplate.queryForObject(
                    query,
                    new UserMapper(),
                    refreshToken
            );
        } catch (EmptyResultDataAccessException err) {
            log.info("[FIND BY REFRESH TOKEN] {}", err.getMessage());
            return null;
        }
    }

    @Override
    public Collection<User> findUserByRole(String role) {
        try {
            return jdbcTemplate.query(
                    QUERY_USER_BY_ROLE,
                    new UserMapper(),
                    role
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ROLE]: {}", err.getMessage());
            return null;
        }
    }
}

