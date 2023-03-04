package com.laptech.restapi.controller;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Cart;
import com.laptech.restapi.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Api(tags = "Store product item of user", value = "Cart Controller")
@CrossOrigin(value = {"*"})
@RequestMapping("/api/v1")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @ApiOperation(value = "Get cart of user (Only 1 cart)", response = Cart.class)
    @GetMapping("/users/{userId}/cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> getCartOfUser(@PathVariable("userId") long userId) {
        return DataResponse.getObjectSuccess(
                "Get cart of user",
                cartService.findById(userId)
        );
    }

    @ApiOperation(value = "Create new cart for user", response = DataResponse.class)
    @PostMapping("/cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> createNewCart(@RequestBody Cart cart) {
        return DataResponse.success(
                "Create new cart",
                cartService.insert(cart)
        );
    }

    @ApiOperation(value = "Update cart information", response = BaseResponse.class)
    @PutMapping("/users/{userId}/cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateCart(@PathVariable("userId") long userId,
                                                   @RequestBody Cart cart) {
        cartService.update(cart, userId);
        return DataResponse.success("Update cart");
    }

    @ApiOperation(value = "Remove old cart (or transform to invoice table)", response = BaseResponse.class)
    @DeleteMapping("/users/{userId}/cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> deleteCart(@PathVariable("userId") long userId,
                                                   @RequestBody(required = false) Map<String, String> body) {
        cartService.delete(userId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete cart");
    }
}
