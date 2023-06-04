package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.jwt.dto.JwtRequest;
import com.laptech.restapi.jwt.dto.JwtResponse;
import com.laptech.restapi.jwt.dto.TokenRefreshResponse;
import com.laptech.restapi.jwt.service.JwtService;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

/**
 * @since 2022-01-04
 */

@Api(tags = "Authentication Controller")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

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

    @PostMapping(value="changePassword")
    public ResponseEntity<BaseResponse> changePassword(HttpServletRequest request,
                                                       @RequestBody Map<String, String> body) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findUserByPhone(principal.getName());
        user.setPassword(body.get("password"));
        userService.update(user, user.getId());
        return DataResponse.success("Change password");
    }

    @PostMapping(value="forgotPassword")
    public ResponseEntity<BaseResponse> forgotPassword(@RequestBody Map<String, String> body) {
        // some security questions here!
        return DataResponse.success("Change password");
    }

    @PostMapping(value="resetPassword")
    public ResponseEntity<BaseResponse> resetPassword(@RequestBody Map<String, String> body) {
        return DataResponse.success("Change password");
    }
}
