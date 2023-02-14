package com.laptech.restapi.jwt.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @since 2023-02-08
 */
@Getter
@Setter
public class TokenRefreshResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
