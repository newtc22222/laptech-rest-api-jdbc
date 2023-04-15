package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.jwt.dto.JwtRequest;
import com.laptech.restapi.jwt.dto.JwtResponse;
import com.laptech.restapi.jwt.dto.TokenRefreshResponse;
import com.laptech.restapi.jwt.service.JwtService;
import com.laptech.restapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @since 2022-01-04
 */

@Api(tags = "Authentication Controller")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @ApiOperation(value = "Create new user (register)", response = DataResponse.class)
    @PostMapping(value = {"/register", "/signUp", "/sign-up"})
    public ResponseEntity<DataResponse> register(@Valid @RequestBody UserDTO dto) {
        return DataResponse.success(
                "Register new user",
                userService.insert(UserDTO.transform(dto))
        );
    }

    @ApiOperation(value = "Handle login for an user", response = JwtResponse.class)
    @PostMapping("/login")
    public ResponseEntity<DataResponse> login(@RequestBody JwtRequest jwtRequest) {
        if (jwtRequest.getPhone().startsWith("0")) {
            jwtRequest.setPhone(jwtRequest.getPhone().replaceAll("^.", "+84"));
        }
        return DataResponse.getObjectSuccess(
                "Login",
                jwtService.createJwtToken(jwtRequest)
        );
    }

    @ApiOperation(value = "Use refresh token to get new accessToken", response = TokenRefreshResponse.class)
    @PostMapping(value = {"/refreshToken", "/refresh-token"})
    public ResponseEntity<DataResponse> refreshToken(@RequestBody Map<String, String> body) {
        return DataResponse.getObjectSuccess(
                "Refresh Token",
                jwtService.refreshJwtToken(body.get("refreshToken"))
        );
    }
}
