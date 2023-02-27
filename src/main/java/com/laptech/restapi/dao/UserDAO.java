package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.UserFilter;
import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.model.User;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface UserDAO extends BaseDAO<User, UserFilter, Long> {
    int updateInformation(UserDTO userDTO);
    int updatePassword(User user);

    int enable(long userId);

    int disable(long userId);

    User findUserByPhone(String phone);

    User findUserByRefreshToken(String refreshToken);

    Collection<User> findUserByRole(String role);
}
