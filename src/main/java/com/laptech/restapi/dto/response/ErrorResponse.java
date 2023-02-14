package com.laptech.restapi.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
@Getter
@Setter
public class ErrorResponse extends BaseResponse {
    private LocalDateTime timestamp;
    private List<String> detail;

    public ErrorResponse(HttpStatus status, String message, List<String> detail) {
        super(status, message);
        this.timestamp = LocalDateTime.now();
        this.detail = detail;
    }
}
