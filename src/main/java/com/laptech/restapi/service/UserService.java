package com.laptech.restapi.service;

import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.model.User;

import java.util.List;

public interface UserService extends BaseService<User, Long> {
    void updateInformation(UserDTO user, long userId);

    void enable(long userId, String updateBy);

    void disable(long userId, String updateBy);

    void insertRole(long userId, int roleId);

    void updateMultipleRoles(long userId, List<Integer> roleIdAddList, List<Integer> roleIdRemoveList);

    void removeRole(long userId, int roleId);

    User findUserByPhone(String phone);
}
