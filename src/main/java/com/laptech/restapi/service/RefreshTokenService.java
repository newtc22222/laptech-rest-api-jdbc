package com.laptech.restapi.service;

import com.laptech.restapi.model.RefreshToken;

import java.util.Collection;

public interface RefreshTokenService {
    void insert(RefreshToken token);

    void delete(String code);

    int count();

    Collection<RefreshToken> findAll(Long page, Long size);

    RefreshToken findRefreshTokenByCode(String code);

    Collection<RefreshToken> findRefreshTokenByUserId(long userId);
}
