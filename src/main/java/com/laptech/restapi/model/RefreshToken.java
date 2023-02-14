package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @since 2023-02-08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private String code;
    private Long userId;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;

    @Override
    public String toString() {
        return "RefreshToken{" +
                "refreshToken='" + code + '\'' +
                ", userId='" + userId + '\'' +
                ", createdDate=" + createdDate +
                ", expiredDate=" + expiredDate +
                '}';
    }
}
