package com.laptech.restapi.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptech.restapi.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://github.com/isopropylcyanide/Jwt-Spring-Security-JPA/issues/7">Link issue</a>
 * <a href="https://github.com/szerhusenBC/jwt-spring-security-demo/issues/44">Link issue</a>
 *
 * @since 2023-02-09
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        final String expiredMsg = (String) request.getAttribute("expired");
        final String signatureMsg = (String) request.getAttribute("signature");
        List<String> errorList = new ArrayList<>();
        if (expiredMsg != null) errorList.add(expiredMsg);
        if (signatureMsg != null) errorList.add(signatureMsg);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized. System need a valid token!", errorList);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }
}
