package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.model.User;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface UserDAO extends BaseDAO<User, Long> {
    int updateInformation(UserDTO userDTO);

    int enable(long userId);

    int disable(long userId);

    User findUserByPhone(String phone);

    User findUserByRefreshToken(String refreshToken);

    List<User> findUserByName(String name);

    List<User> findUserByGender(Gender gender);

    List<User> findUserByRole(String role);
}
