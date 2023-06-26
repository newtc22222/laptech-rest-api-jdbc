package com.laptech.restapi.controller;

import com.laptech.restapi.common.exception.ForbiddenException;
import com.laptech.restapi.common.exception.InvalidArgumentException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.common.exception.TokenInvalidException;
import com.laptech.restapi.dto.request.ForgetPasswordRequestDTO;
import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.jwt.dto.JwtRequest;
import com.laptech.restapi.jwt.dto.JwtResponse;
import com.laptech.restapi.jwt.dto.TokenRefreshResponse;
import com.laptech.restapi.jwt.service.JwtService;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.UserService;
import com.laptech.restapi.service.email.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * @since 2022-01-04
 */

@Api(tags = "Authentication Controller")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final EmailService emailService;
    private final UserService userService;
    private final JwtService jwtService;

    @ApiOperation(value = "Create new user (register)", response = DataResponse.class)
    @PostMapping(value = {"/register"})
    public ResponseEntity<DataResponse> register(@Valid @RequestBody UserDTO dto) {
        return DataResponse.success(
                "Register new user",
                userService.insert(UserDTO.transform(dto))
        );
    }

    @ApiOperation(value = "Handle login for an user", response = JwtResponse.class)
    @PostMapping("/login")
    public ResponseEntity<DataResponse> login(HttpServletRequest request,
                                              @RequestBody JwtRequest jwtRequest) {
        String userAgent = request.getHeader("User-Agent");
        String userIpAddress = request.getRemoteAddr();
        System.out.printf("Device: %s\nIP: %s", userAgent, userIpAddress);

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

    @PostMapping(value = "changePassword")
    public ResponseEntity<BaseResponse> changePassword(HttpServletRequest request,
                                                       @RequestBody Map<String, String> body) {
        Principal principal = request.getUserPrincipal();
        // check
        jwtService.authenticate(principal.getName(), body.get("oldPassword"));
        // change
        User user = userService.findUserByPhone(principal.getName());
        user.setPassword(body.get("newPassword"));
        userService.update(user, user.getId());
        return DataResponse.success("Change password");
    }

    @PostMapping(value = "forgotPassword")
    public ResponseEntity<?> forgotPassword(HttpServletRequest request,
                                            @RequestBody ForgetPasswordRequestDTO dto) {
        // some security questions here!
        User user = userService.findUserByPhone(dto.getPhone());
        if (user == null) {
            throw new ResourceNotFoundException("Can not find user with phone = " + dto.getPhone() + " in system!");
        }
        if (!user.isActive()) {
            throw new ForbiddenException("[Error] This account has been blocked by admin!\nIf this is error, please contact with admin!");
        }
        // create, add token to db and build link
        String token = UUID.randomUUID().toString();

        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            // another method to reset - username + account create date
            if (user.getCreatedDate().toLocalDate().equals(dto.getAccountCreatedDate())
                && user.getName().equals(dto.getUsername())) {
                userService.insertPasswordResetToken(user.getId(), token);
                return ResponseEntity.ok("Your reset password token is\n"
                        + token
                        + "\nUse this to set new password");
            }
            throw new InvalidArgumentException("Your information is not correct!");
        }
        // user have email
        if (user.getEmail().equals(dto.getEmail())) {
            userService.insertPasswordResetToken(user.getId(), token);
            emailService.sendPasswordResetEmail(
                    request,
                    getPasswordResetUrl(getApplicationUrl(request), token),
                    token,
                    user.getEmail()
            );
        } else {
            throw new ResourceNotFoundException("This email is not existed in system!");
        }
        return DataResponse.success("Password reset link send");
    }

    @PostMapping(value = "updatePassword")
    public ResponseEntity<BaseResponse> updatePassword(@RequestParam String token,
                                                       @RequestBody Map<String, String> body) {
        // find user with token
        User user = userService.findUserByPasswordResetToken(token);
        if(user == null) {
            throw new TokenInvalidException("Your token is not correct!");
        }
        String newPassword = body.get("newPassword");
        user.setPassword(newPassword);
        userService.update(user, user.getId());
        return DataResponse.success("Update password");
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName() + ":"
                + request.getServerPort()
                + request.getContextPath();
    }

    private String getPasswordResetUrl(String applicationUrl, String token) {
        return applicationUrl + "/api/v1/auth" + "/updatePassword?token=" + token;
    }
}
