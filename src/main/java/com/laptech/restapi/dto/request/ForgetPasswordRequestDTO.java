package com.laptech.restapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequestDTO {
    private String phone;
    private String email;
    private String username;
    private LocalDate accountCreatedDate;
}
