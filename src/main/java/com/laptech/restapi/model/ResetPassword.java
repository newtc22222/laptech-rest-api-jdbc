package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    private Long id;
    private Long userId;
    private String token;
    private LocalDateTime expiredTime;
    private LocalDateTime createdDate;

    @Override
    public String toString() {
        return "ResetPassword{" +
                "id=" + id +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", expiredTime=" + expiredTime +
                ", createdDate=" + createdDate +
                '}';
    }
}
