package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Api(tags = "User information in system", value = "User controller")
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get all users in system", response = User.class)
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getAllUsers(@RequestParam(required = false) String sortBy,
                                                    @RequestParam(required = false) String sortDir,
                                                    @RequestParam(required = false) Long page,
                                                    @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all users",
                userService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get user with filter", response = User.class)
    @GetMapping("/users/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getUserWithFilter(@RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "gender", required = false) String gender,
                                                          @RequestParam(value = "role", required = false) String role) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("gender", gender);
        params.put("role", role);
        return DataResponse.getCollectionSuccess(
                "Get user with filter",
                userService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get an user with id", response = User.class)
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<DataResponse> findUserById(@PathVariable("id") Long userId) {
        return DataResponse.getObjectSuccess(
                "Get user",
                userService.findById(userId)
        );
    }

    @ApiIgnore
    @ApiOperation(value = "Get an user with phone", response = User.class)
    @GetMapping("/users/phone/{phone}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DataResponse> findUserByPhone(@PathVariable("phone") String phone) {
        return DataResponse.getObjectSuccess(
                "Get user with phone",
                userService.findUserByPhone(phone)
        );
    }

    @ApiOperation(value = "Update all information of user", response = BaseResponse.class)
    @PutMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BaseResponse> updateAllForUser(@PathVariable("id") long userId,
                                                         @RequestBody Map<String, String> userRequest) {
        userService.update(UserDTO.transform(userRequest), userId);
        return DataResponse.success("Update user's detail");
    }

    @ApiOperation(value = "Update some information", response = BaseResponse.class)
    @PatchMapping(value = "{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateUserInformation(@PathVariable("id") long userId,
                                                              @RequestBody Map<String, String> userRequest) {
        userService.updateInformation(UserDTO.getData(userRequest), userId);
        return DataResponse.success("Update user's information");
    }

    @ApiOperation(value = "Activate user (if block)", response = BaseResponse.class)
    @GetMapping("/users/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> enableUser(@PathVariable("id") long userId,
                                                   @RequestBody(required = false) Map<String, String> body) {
        userService.enable(userId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Activate user");
    }

    @ApiOperation(value = "Block user", response = BaseResponse.class)
    @GetMapping("/users/{id}/block")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> disableUser(@PathVariable("id") long userId,
                                                    @RequestBody(required = false) Map<String, String> body) {
        userService.disable(userId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Block user");
    }

    @ApiIgnore
    @ApiOperation(value = "Delete user in system", response = BaseResponse.class)
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable("id") long userId,
                                                   @RequestBody(required = false) Map<String, String> body) {
        userService.delete(userId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete user");
    }

    @ApiOperation(value = "Grant role for user", response = BaseResponse.class)
    @PostMapping("/users/{id}/role")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> addRoleForUser(@PathVariable("id") long userId,
                                                       @RequestBody Map<String, Integer> roleRequest) {
        userService.insertRole(userId, roleRequest.get("roleId"));
        return DataResponse.success("Add role for user");
    }

    @ApiOperation(value = "Revoke role of user", response = BaseResponse.class)
    @DeleteMapping("/users/{id}/role")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> removeRoleOfUser(@PathVariable("id") long userId,
                                                         @RequestBody Map<String, Integer> roleRequest) {
        userService.removeRole(userId, roleRequest.get("roleId"));
        return DataResponse.success("Remove role for user");
    }
}
