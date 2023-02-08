package com.laptech.restapi.jwt.dto;

/**
 * @author Nhat Phi
 * @since 2023-01-04
 */
public class JwtRequest {
    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
