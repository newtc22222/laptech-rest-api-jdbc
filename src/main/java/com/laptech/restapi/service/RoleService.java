package com.laptech.restapi.service;

import com.laptech.restapi.model.Role;

import java.util.List;

/**
 * @since 2023-02-06
 */
public interface RoleService extends BaseService<Role, Integer> {
    List<Role> findRoleByUserId(long userId);
}
