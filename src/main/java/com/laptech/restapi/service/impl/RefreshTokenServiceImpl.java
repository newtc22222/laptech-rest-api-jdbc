package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.RefreshTokenDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.model.RefreshToken;
import com.laptech.restapi.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @since 2023-02-08
 */
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenDAO refreshTokenDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void insert(RefreshToken token) {
        if (refreshTokenDAO.insert(token) == 0) {
            throw new InternalServerErrorException("[Error] Cannot insert refresh token to database!");
        }
    }

    @Override
    public void delete(String code) {
        if (refreshTokenDAO.delete(code) == 0) {
            throw new InternalServerErrorException("[Error] Cannot delete refresh token from database!");
        }
    }

    @Override
    public int count() {
        return refreshTokenDAO.count();
    }

    @Override
    public List<RefreshToken> findAll(Long page, Long size) {
        if (size == null)
            return refreshTokenDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return refreshTokenDAO.findAll(limit, skip);
    }

    @Override
    public RefreshToken findRefreshTokenByCode(String code) {
        return refreshTokenDAO.findRefreshTokenByCode(code);
    }

    @Override
    public List<RefreshToken> findRefreshTokenByUserId(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        return refreshTokenDAO.findRefreshTokenByUserId(userId);
    }
}
