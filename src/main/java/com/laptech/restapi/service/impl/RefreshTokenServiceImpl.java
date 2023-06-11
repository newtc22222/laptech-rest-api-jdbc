package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.RefreshTokenDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.model.RefreshToken;
import com.laptech.restapi.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @since 2023-02-08
 */
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenDAO refreshTokenDAO;
    private final UserDAO userDAO;

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
    public Collection<RefreshToken> findAll(Long page, Long size) {
        if(page == null && size == null) {
            return refreshTokenDAO.findAll();
        }
        return refreshTokenDAO.findAll(size, (Objects.requireNonNull(page) - 1) * size);
    }

    @Override
    public RefreshToken findRefreshTokenByCode(String code) {
        return refreshTokenDAO.findRefreshTokenByCode(code);
    }

    @Override
    public Collection<RefreshToken> findRefreshTokenByUserId(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        return refreshTokenDAO.findRefreshTokenByUserId(userId);
    }
}
