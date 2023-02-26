package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.RoleFilter;
import com.laptech.restapi.model.Role;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
public interface RoleDAO extends BaseDAO<Role, RoleFilter, Integer> {
    Role findRoleByName(String name);

    List<Role> findRoleByUserId(long userId);
}
