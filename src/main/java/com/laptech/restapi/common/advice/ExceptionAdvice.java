package com.laptech.restapi.common.advice;

import com.laptech.restapi.common.exception.*;
import com.laptech.restapi.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-01-06
 */
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException ex, WebRequest req) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid input data", detail));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND, "Resource Not Found", detail));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest req) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Resource Already Exist", detail));
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorResponse> handlerDataAccessException(InvalidArgumentException ex, WebRequest req) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid data", detail));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handlerInternalServerErrorException(InternalServerErrorException ex, WebRequest req) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Server error", detail));
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ErrorResponse> handlerTokenInvalidException(TokenInvalidException ex, WebRequest req) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid token", detail));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handlerForbiddenException(ForbiddenException ex, WebRequest req) {
        List<String> detail = new ArrayList<>();
        detail.add(ex.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ErrorResponse(HttpStatus.FORBIDDEN, "Deny access to information", detail));
    }
}
