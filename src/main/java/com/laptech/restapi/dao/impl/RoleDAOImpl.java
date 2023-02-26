package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.RoleDAO;
import com.laptech.restapi.mapper.RoleMapper;
import com.laptech.restapi.model.Role;
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
 * @since 2023-02-02
 */
@Transactional
@Log4j2
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
    @Value("${sp_FindAllRolesLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindRoleById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindRoleByName}")
    private String QUERY_ONE_BY_NAME;
    @Value("${sp_FindRoleByUserId}")
    private String QUERY_ROLE_BY_USER_ID;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where name=?", "tbl_role"); // maybe upgrade

    @Override
    public Integer insert(Role role) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Integer.class,
                    role.getName(),
                    role.getDescription()
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
                    role.getDescription()
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
                    roleId
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
    public boolean isExists(Role role) {
        try {
            Role existsRole = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new RoleMapper(),
                    role.getName()
            );
            return existsRole != null;
        } catch (DataAccessException err) {
            log.error("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Role> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new RoleMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Role> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new RoleMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND LIMIT] {}", err.getLocalizedMessage());
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
        } catch (EmptyResultDataAccessException err) {
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
        } catch (EmptyResultDataAccessException err) {
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
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY USER ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
