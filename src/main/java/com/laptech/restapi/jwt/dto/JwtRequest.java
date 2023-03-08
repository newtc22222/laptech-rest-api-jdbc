package com.laptech.restapi.jwt.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nhat Phi
 * @since 2023-01-04
 */
@Getter
@Setter
public class JwtRequest {
    private String phone;
    private String password;
}
