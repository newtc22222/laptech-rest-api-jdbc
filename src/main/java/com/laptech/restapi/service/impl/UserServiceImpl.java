package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.enums.RoleType;
import com.laptech.restapi.common.exception.*;
import com.laptech.restapi.dao.RoleDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dao.UserRoleDAO;
import com.laptech.restapi.dto.filter.UserFilter;
import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.UserService;
import com.laptech.restapi.util.AuditUtil;
import com.laptech.restapi.util.ConvertMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    private final RoleDAO roleDAO;
    private final UserRoleDAO userRoleDAO;

    @Override
    public User insert(User user) {
        if (userDAO.isExists(user)) {
            throw new ResourceAlreadyExistsException("[Info] This user has already existed in system!");
        }
        if (userDAO.findUserByPhone(user.getPhone()) != null) {
            throw new ResourceAlreadyExistsException("[Info] This phone has already existed in system!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Long newUserId = userDAO.insert(user);
        if (newUserId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new user!");
        }
        userRoleDAO.insert(newUserId, roleDAO.findRoleByName(RoleType.USER.toString()).getId());
        return userDAO.findById(newUserId);
    }

    @Override
    public void update(User user, Long userId) {
        User oldUser = userDAO.findById(userId);
        if (oldUser == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            oldUser.setName(user.getName());
            oldUser.setGender(user.getGender());
            oldUser.setDateOfBirth(user.getDateOfBirth());
            oldUser.setPhone(user.getPhone());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            oldUser.setUpdateBy(user.getUpdateBy());

            if (userDAO.update(oldUser) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update user!");
            }
        }
    }

    @Override
    public void delete(Long userId, String updateBy) {
        User oldUser = userDAO.findById(userId);
        if (oldUser == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            if (oldUser.isActive()) {
                throw new BadRequestException("[Info] You can't delete an active user!");
            }
            if (userDAO.delete(userId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to delete user!");
            }
        }
    }

    @Override
    public long count() {
        return userDAO.count();
    }

    @Override
    public Collection<User> findAll(String sortBy, String sortDir, Long page, Long size) {
        return userDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<User> findWithFilter(Map<String, Object> params) {
        UserFilter filter = new UserFilter(ConvertMap.changeAllValueFromObjectToString(params));
        Set<User> userSet = (Set<User>) userDAO.findWithFilter(filter);

        if (params.containsKey("role")) {
            Collection<User> userList = userDAO.findUserByRole(params.get("role").toString());
            userSet.removeIf(user -> !userList.contains(user));
        }
        return userSet;
    }

    @Override
    public User findById(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        return user;
    }

    @Override
    public void updateInformation(UserDTO user, long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            user.setId(userId);
            if (userDAO.updateInformation(user) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update user's information!");
            }
        }
    }

    @Override
    public void enable(long userId, String updateBy) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            if (userDAO.enable(userId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to enable user!");
            }
        }
    }

    @Override
    public void disable(long userId, String updateBy) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            if (userDAO.disable(userId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to disable user!");
            }
        }
    }

    @Override
    public void insertRole(long userId, int roleId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        if (roleDAO.findById(roleId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find role with id=" + roleId);
        }
        if (userRoleDAO.insert(userId, roleId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to insert role into user!");
        }
    }

    @Override
    public void updateMultipleRoles(long userId, List<Integer> roleIdAddList, List<Integer> roleIdRemoveList) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        for(List<Integer> roleIds: Arrays.asList(roleIdAddList, roleIdRemoveList)) {
            roleIds.forEach(roleId -> {
                if(roleDAO.findById(roleId) == null)
                    throw new ResourceNotFoundException("[Info] Cannot find role with id=" + roleId);
            });
        }
        if (userRoleDAO.updateMultiple(userId, roleIdAddList, roleIdRemoveList) == 0) {
            throw new InternalServerErrorException("[Error] Failed to update roles of user!");
        }
    }

    @Override
    public void removeRole(long userId, int roleId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        if (roleDAO.findById(roleId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find role with id=" + roleId);
        }
        if (userRoleDAO.remove(userId, roleId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to remove role from user!");
        }
    }

    @Override
    public User findUserByPhone(String phone) {
        String auditPhone = AuditUtil.getPhoneAudit(phone);
        if (!auditPhone.equals("")) {
            throw new InvalidArgumentException(auditPhone);
        }
        return userDAO.findUserByPhone(phone);
    }
}
