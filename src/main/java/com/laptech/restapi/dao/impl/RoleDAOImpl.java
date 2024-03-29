package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.RoleDAO;
import com.laptech.restapi.dto.filter.RoleFilter;
import com.laptech.restapi.mapper.RoleMapper;
import com.laptech.restapi.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class RoleDAOImpl implements RoleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewRole}")
    private String INSERT;
    @Value("${sp_UpdateRole}")
    private String UPDATE;
    @Value("${sp_DeleteRole}")
    private String DELETE;

    @Value("${sp_FindAllRoles}")
    private String QUERY_ALL;
    @Value("${sp_FindRoleByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindRoleById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindRoleByName}")
    private String QUERY_ONE_BY_NAME;
    @Value("${sp_FindRoleByUserId}")
    private String QUERY_ROLE_BY_USER_ID;
    @Value("${sp_CheckExistRole}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllRole}")
    private String COUNT_ALL;
    @Value("${sp_CountRoleWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public Integer insert(Role role) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Integer.class,
                    role.getName(),
                    role.getDescription(),
                    role.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Role role) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    role.getId(),
                    role.getName(),
                    role.getDescription(),
                    role.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Integer roleId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    roleId,
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
    public long countWithFilter(RoleFilter filter) {
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
    public boolean isExists(Role role) {
        try {
            Collection<Role> existsRole = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new RoleMapper(),
                    role.getName()
            );
            return existsRole.size() > 0;
        } catch (DataAccessException err) {
            log.error("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Role> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new RoleMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Role> findWithFilter(RoleFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new RoleMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
            log.error("[FIND FILTER] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Role findById(Integer roleId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new RoleMapper(),
                    roleId
            );
        } catch (DataAccessException err) {
            log.error("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Role findRoleByName(String name) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_NAME,
                    new RoleMapper(),
                    name
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY NAME] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Role> findRoleByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ROLE_BY_USER_ID,
                    new RoleMapper(),
                    userId
            );
        } catch (DataAccessException err) {
            log.error("[FIND BY USER ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
