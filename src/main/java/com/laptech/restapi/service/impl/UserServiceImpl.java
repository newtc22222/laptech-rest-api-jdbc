package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.common.enums.RoleType;
import com.laptech.restapi.common.exception.*;
import com.laptech.restapi.dao.RoleDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dao.UserRoleDAO;
import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.UserService;
import com.laptech.restapi.util.AuditUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;

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
        // check

        User oldUser = userDAO.findById(userId);
        if (oldUser == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            oldUser.setName(user.getName());
            oldUser.setGender(user.getGender());
            oldUser.setDateOfBirth(user.getDateOfBirth());
            oldUser.setPhone(user.getPhone());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(user.getPassword());

            if (userDAO.update(oldUser) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update user!");
            }
        }
    }

    @Override
    public void delete(Long userId) {
        User oldUser = userDAO.findById(userId);
        if (oldUser == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            if (oldUser.isActive()) {
                throw new BadRequestException("[Info] You can't delete an active user!");
            }
            if (userDAO.delete(userId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to delete user!");
            }
        }
    }

    @Override
    public List<User> findAll(Long page, Long size) {
        if (size == null)
            return userDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return userDAO.findAll(limit, skip);
    }

    @Override
    public User findById(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
//        User currentUser = AuthenticateUserUtil.getCurrentUser();
//        if(currentUser == null) {
//            throw new BadRequestException("[Info] You need to login before using this system!");
//        }
//        if(currentUser.getId() != userId) {
//            throw new ForbiddenException("[Error] You can't have grand for access to this account");
//        }
        return user;
    }

    @Override
    public void updateInformation(UserDTO user, long userId) {
        // check

        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            if (userDAO.updateInformation(user) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update user's information!");
            }
        }
    }

    @Override
    public void enable(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            if (userDAO.enable(userId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to enable user!");
            }
        }
    }

    @Override
    public void disable(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        } else {
            if (userDAO.disable(userId) == 0) {
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

    @Override
    public Set<User> filter(Map<String, String> params) {
        Set<User> userSet = new HashSet<>(userDAO.findAll());
        if (params.containsKey("name")) {
            List<User> userList = userDAO.findUserByName(params.get("name"));
            userSet.removeIf(user -> !userList.contains(user));
        }
        if (params.containsKey("gender")) {
            List<User> userList = userDAO.findUserByGender(Gender.valueOf(params.get("gender")));
            userSet.removeIf(user -> !userList.contains(user));
        }
        if (params.containsKey("role")) {
            List<User> userList = userDAO.findUserByRole(params.get("role"));
            userSet.removeIf(user -> !userList.contains(user));
        }
        return userSet;
    }
}
