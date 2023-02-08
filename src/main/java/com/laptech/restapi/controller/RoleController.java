package com.laptech.restapi.controller;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Role;
import com.laptech.restapi.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @since 2023-02-07
 */
@Api(tags = "Role of user in system", value = "Role controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Get all role in system", response = Role.class)
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRole(@RequestParam(required = false, defaultValue = "1") Long page,
                                                 @RequestParam(required = false) Long size) {
        return ResponseEntity.ok(roleService.findAll(page, size));
    }

    @ApiOperation(value = "Get roles of user in system", response = Role.class)
    @GetMapping("/users/{userId}/role")
    public ResponseEntity<List<Role>> getRolesOfUser(@PathVariable(value = "userId") long userId) {
        return ResponseEntity.ok(roleService.findRoleByUserId(userId));
    }

    @ApiOperation(value = "Get a role with id", response = Role.class)
    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") int roleId) {
        return ResponseEntity.ok(roleService.findById(roleId));
    }

    @ApiOperation(value = "Create new role", response = DataResponse.class)
    @PostMapping("/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewRole(@RequestBody Role role) {
        return DataResponse.success(
                "Create new role",
                roleService.insert(role)
        );
    }

    @ApiOperation(value = "Update role's detail", response = BaseResponse.class)
    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateRole(@PathVariable(value = "id") int roleId,
                                                   @RequestBody Role role) {
        roleService.update(role, roleId);
        return DataResponse.success("Update role");
    }

    @ApiOperation(value = "Delete role in system", response = BaseResponse.class)
    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteRole(@PathVariable(value = "id") int roleId) {
        roleService.delete(roleId);
        return DataResponse.success("Delete role");
    }
}
