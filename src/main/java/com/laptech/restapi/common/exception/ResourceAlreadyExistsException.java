package com.laptech.restapi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Nhat Phi
 * @since 2023-02-03
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String exception) {
        super(exception);
    }
}
