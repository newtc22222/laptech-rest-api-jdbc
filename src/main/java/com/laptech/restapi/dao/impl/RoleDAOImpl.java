package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.RoleDAO;
import com.laptech.restapi.mapper.RoleMapper;
import com.laptech.restapi.model.Role;
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

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

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

    private final String TABLE_NAME = "tbl_role";
    private final String INSERT = String.format("insert into %s values (0, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set name=?, description=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=?", TABLE_NAME);
    private final String QUERY_ONE_BY_NAME = String.format("select * from %s where name=?", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where name=?", TABLE_NAME); // maybe upgrade

    private final String QUERY_ROLE_BY_USER_ID = String.format("select r.* from %s ur, %s r where ur.user_id=? and ur.role_id=r.id",
            "tbl_user_role", TABLE_NAME);

    @Override
    public Integer insert(Role role) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, role.getName());
                ps.setString(2, role.getDescription());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Role role) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    role.getName(),
                    role.getDescription(),
                    role.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(Integer roleId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    roleId
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
    public boolean isExists(Role role) {
        try {

            Role existsRole = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new RoleMapper(),
                    role.getName()
            );
            return existsRole != null;
        } catch (DataAccessException err) {
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn("[FIND BY NAME]: {}", err.getLocalizedMessage());
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
            log.warn(err);
            return null;
        }
    }
}
