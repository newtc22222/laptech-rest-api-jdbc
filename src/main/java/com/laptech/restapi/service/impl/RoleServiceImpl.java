package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.RoleDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dto.filter.RoleFilter;
import com.laptech.restapi.model.Role;
import com.laptech.restapi.service.RoleService;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @since 2023-02-06
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Role insert(Role role) {
        // check

        if (roleDAO.isExists(role)) {
            throw new ResourceAlreadyExistsException("[Info] This role had already existed in system!");
        }
        Integer newRoleId = roleDAO.insert(role);
        if (newRoleId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new role!");
        }
        return roleDAO.findById(newRoleId);
    }

    @Override
    public void update(Role role, Integer roleId) {
        Role oldRole = roleDAO.findById(roleId);

        if (oldRole == null) {
            throw new ResourceNotFoundException("[Info] Cannot find role with id=" + roleId);
        } else {
            oldRole.setName(role.getName());
            oldRole.setDescription(role.getDescription());

            if (roleDAO.update(oldRole) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this role!");
            }
        }
    }

    @Override
    public void delete(Integer roleId, String updateBy) {
        if (roleDAO.findById(roleId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find role with id=" + roleId);
        } else {
            if (roleDAO.delete(roleId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this role!");
            }
        }
    }

    @Override
    public long count() {
        return roleDAO.count();
    }

    @Override
    public Collection<Role> findAll(String sortBy, String sortDir, Long page, Long size) {
        return roleDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Role> findWithFilter(Map<String, Object> params) {
        RoleFilter filter = new RoleFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return roleDAO.findWithFilter(filter);
    }

    @Override
    public Role findById(Integer roleId) {
        Role role = roleDAO.findById(roleId);
        if (role == null) {
            throw new ResourceNotFoundException("[Info] Cannot find role with id=" + roleId);
        }
        return role;
    }

    @Override
    public List<Role> findRoleByUserId(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        return roleDAO.findRoleByUserId(userId);
    }
}
