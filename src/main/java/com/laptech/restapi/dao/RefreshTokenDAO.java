package com.laptech.restapi.dao;

import com.laptech.restapi.model.RefreshToken;

import java.util.Collection;

/**
 * @since 2023-02-08
 */
public interface RefreshTokenDAO {
    int insert(RefreshToken token);

    int delete(String code);

    int count();

    Collection<RefreshToken> findAll();

    Collection<RefreshToken> findAll(long limit, long skip);

    RefreshToken findRefreshTokenByCode(String code);

    Collection<RefreshToken> findRefreshTokenByUserId(long userId);
}
