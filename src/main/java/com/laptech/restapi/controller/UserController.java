package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.UserDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Cart;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.CartService;
import com.laptech.restapi.service.InvoiceService;
import com.laptech.restapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
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

    @Autowired
    private CartService cartService;
    @Autowired
    private InvoiceService invoiceService;

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
    public ResponseEntity<DataResponse> getUserWithFilter(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String gender, // can upgrade
                                                          @RequestParam(required = false) String dateOfBirth,
                                                          @RequestParam(required = false) String email,
                                                          @RequestParam(required = false) Boolean isActive,
                                                          @RequestParam(required = false) String role, // can upgrade
                                                          @RequestParam(required = false) String createdDate,
                                                          @RequestParam(required = false) String modifiedDate,
                                                          @RequestParam(required = false) String deletedDate,
                                                          @RequestParam(required = false) Boolean isDel,
                                                          @RequestParam(required = false) String updateBy,
                                                          @RequestParam(required = false) String sortBy,
                                                          @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("gender", gender);
        params.put("dateOfBirth", dateOfBirth);
        params.put("email", email);
        params.put("isActive", isActive);
        params.put("role", role);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);

        return DataResponse.getCollectionSuccess(
                "Get user with filter",
                userService.findWithFilter(params)
        );
    }

    /* For current user */
    @ApiOperation(value = "Get current user in system!", response = User.class)
    @GetMapping("getCurrentUser")
    public ResponseEntity<DataResponse> getCurrentUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return DataResponse.getObjectSuccess(
                "Get current user",
                userService.findUserByPhone(principal.getName())
        );
    }

    @ApiOperation(value = "Change current user data in system!", response = BaseResponse.class)
    @PatchMapping("changeMyInformation")
    public ResponseEntity<BaseResponse> currentUserChange(HttpServletRequest request,
                                                          @RequestBody Map<String, String> userRequest) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findUserByPhone(principal.getName());
        userService.updateInformation(UserDTO.getData(userRequest), user.getId());
        return DataResponse.success("Update current user");
    }

    @ApiOperation(value = "Get cart of current user", response = Cart.class)
    @GetMapping("/myCart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> getCartOfCurrentUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User currentUser = userService.findUserByPhone(principal.getName());
        return DataResponse.getObjectSuccess(
                "Get cart of current user",
                cartService.findById(currentUser.getId())
        );
    }

    @ApiOperation(value = "Get all invoices of current user", response = Invoice.class)
    @GetMapping("/myInvoices")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> getInvoicesOfCurrentUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User currentUser = userService.findUserByPhone(principal.getName());
        return DataResponse.getCollectionSuccess(
                "Get invoices of current user",
                invoiceService.getInvoicesOfUser(currentUser.getId())
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

    @ApiOperation(value = "Create new user with admin role", response = DataResponse.class)
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataResponse> createNewUser(@Valid @RequestBody UserDTO dto) {
        return DataResponse.getObjectSuccess(
                "Create new user",
                userService.insert(UserDTO.transform(dto))
        );
    }

    @ApiOperation(value = "Update all information of user", response = BaseResponse.class)
    @PutMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BaseResponse> updateAllForUser(@PathVariable("id") long userId,
                                                         @Valid @RequestBody UserDTO dto) {
        userService.update(UserDTO.transform(dto), userId);
        return DataResponse.success("Update user's detail");
    }

    @ApiOperation(value = "Update some information", response = BaseResponse.class)
    @PatchMapping(value = "/users/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateUserInformation(@PathVariable("id") long userId,
                                                              @RequestBody Map<String, String> userRequest) {
        userService.updateInformation(UserDTO.getData(userRequest), userId);
        return DataResponse.success("Update user's information");
    }

    @ApiOperation(value = "Activate user (if block)", response = BaseResponse.class)
    @PostMapping("/users/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> enableUser(@PathVariable("id") long userId,
                                                   @RequestBody(required = false) Map<String, String> body) {
        userService.enable(userId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Activate user");
    }

    @ApiOperation(value = "Block user", response = BaseResponse.class)
    @PostMapping("/users/{id}/block")
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
    @PostMapping("/users/{id}/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> addRoleForUser(@PathVariable("id") long userId,
                                                       @RequestBody Map<String, Integer> roleRequest) {
        userService.insertRole(userId, roleRequest.get("roleId"));
        return DataResponse.success("Add role for user");
    }

    @ApiOperation(value = "Revoke role of user", response = BaseResponse.class)
    @DeleteMapping("/users/{id}/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> removeRoleOfUser(@PathVariable("id") long userId,
                                                         @RequestBody Map<String, Integer> roleRequest) {
        userService.removeRole(userId, roleRequest.get("roleId"));
        return DataResponse.success("Remove role for user");
    }
}
