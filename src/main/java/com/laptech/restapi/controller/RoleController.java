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

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<DataResponse> getAllRoles(@RequestParam(required = false) String sortBy,
                                                    @RequestParam(required = false) String sortDir,
                                                    @RequestParam(required = false) Long page,
                                                    @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all roles",
                roleService.count(),
                roleService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get role with filter", response = Role.class)
    @GetMapping("/roles/filter")
    public ResponseEntity<DataResponse> getRoleWithFilter() {
        Map<String, Object> params = new HashMap<>();

        return DataResponse.getCollectionSuccess(
                "Get role with filter",
                roleService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get roles of user in system", response = Role.class)
    @GetMapping("/users/{userId}/role")
    public ResponseEntity<DataResponse> getRolesOfUser(@PathVariable(value = "userId") long userId) {
        return DataResponse.getCollectionSuccess(
                "Get role of user",
                roleService.findRoleByUserId(userId)
        );
    }

    @ApiOperation(value = "Get a role with id", response = Role.class)
    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getRoleById(@PathVariable(value = "id") int roleId) {
        return DataResponse.getObjectSuccess(
                "Get role",
                roleService.findById(roleId)
        );
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
    public ResponseEntity<BaseResponse> deleteRole(@PathVariable(value = "id") int roleId,
                                                   @RequestBody(required = false) Map<String, String> body) {
        roleService.delete(roleId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete role");
    }
}
