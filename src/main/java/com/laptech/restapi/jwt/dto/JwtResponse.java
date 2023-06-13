package com.laptech.restapi.jwt.dto;

import com.laptech.restapi.model.Role;
import com.laptech.restapi.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-01-04
 */
@Getter
@Setter
public class JwtResponse {
    private User user;
    private List<Role> roleList;
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
    private long maxAgeToken;

    public JwtResponse(User user, List<Role> roleList, String accessToken, String refreshToken, long maxAgeToken) {
        this.user = user;
        this.roleList = roleList;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.maxAgeToken = maxAgeToken;
    }
}
