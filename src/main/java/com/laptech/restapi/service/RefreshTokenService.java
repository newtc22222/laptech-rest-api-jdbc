package com.laptech.restapi.service;

import com.laptech.restapi.model.RefreshToken;

import java.util.List;

public interface RefreshTokenService {
    void insert(RefreshToken token);
    void delete(String code);

    int count();
    List<RefreshToken> findAll(Long page, Long size);
    RefreshToken findRefreshTokenByCode(String code);
    List<RefreshToken> findRefreshTokenByUserId(long userId);
}
