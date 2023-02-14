package com.laptech.restapi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @since 2023-02-08
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message);
    }
}
