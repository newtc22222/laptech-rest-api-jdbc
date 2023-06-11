package com.laptech.restapi.controller.payment;

import com.laptech.restapi.dto.request.CheckoutDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.service.payment.checkout.CheckoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @since 2023-06-01
 */
@Api(tags = "CHECK OUT REST API", value = "Check out Controller")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @ApiOperation(value = "Check out with all units", response = BaseResponse.class)
    @PostMapping("")
    public ResponseEntity<BaseResponse> checkout(@Valid @RequestBody CheckoutDTO checkoutDTO) {
        checkoutService.checkout(checkoutDTO);
        return DataResponse.success("Checkout");
    }
}
