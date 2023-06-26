package com.laptech.restapi.dao;

import com.laptech.restapi.model.ResetPassword;
import com.laptech.restapi.model.User;

public interface ResetPasswordDAO {
    int insert(ResetPassword resetPassword);
    ResetPassword findResetPasswordTokenByToken(String token);
    User findUserByResetPasswordToken(String token);
}
