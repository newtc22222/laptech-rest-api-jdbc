package com.laptech.restapi.dto.response;

import org.springframework.http.HttpStatus;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */

public class BaseResponse {
    private int status;
    private String message;

    public BaseResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
