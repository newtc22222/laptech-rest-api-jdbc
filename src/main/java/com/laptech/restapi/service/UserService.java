package com.laptech.restapi.service;

import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.model.User;

import java.util.Map;
import java.util.Set;

public interface UserService extends BaseService<User, Long> {
    void updateInformation(UserDTO user, long userId);

    void enable(long userId);

    void disable(long userId);

    void insertRole(long userId, int roleId);

    void removeRole(long userId, int roleId);

    User findUserByPhone(String phone);

    /**
     * Filter options:
     * - name
     * - gender
     * - role
     */
    Set<User> filter(Map<String, String> params);
}
