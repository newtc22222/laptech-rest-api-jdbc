package com.laptech.restapi.dao;

import com.laptech.restapi.model.RefreshToken;

import java.util.List;

/**
 * @since 2023-02-08
 */
public interface RefreshTokenDAO {
    int insert(RefreshToken token);

    int delete(String code);

    int count();

    List<RefreshToken> findAll();

    List<RefreshToken> findAll(long limit, long skip);

    RefreshToken findRefreshTokenByCode(String code);

    List<RefreshToken> findRefreshTokenByUserId(long userId);
}
