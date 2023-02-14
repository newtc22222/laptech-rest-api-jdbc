package com.laptech.restapi.dto.response;

import org.springframework.http.HttpStatus;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */

public class BaseResponse {
    private HttpStatus status;
    private String message;

    public BaseResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
