package com.laptech.restapi.controller;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Address;
import com.laptech.restapi.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Address Of User", value = "address CRUD and get address of User")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "Get address by addressId", response = Address.class)
    @GetMapping("/address/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Address> findAddressById(@PathVariable("id") long addressId) {
        return ResponseEntity.ok(addressService.findById(addressId));
    }

    @ApiOperation(value = "Get list address of user (userId)", response = Address.class)
    @GetMapping("/users/{userId}/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Address>> getAllAddressOfUser(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(addressService.findAddressByUserId(userId));
    }

    @ApiOperation(value = "Add new address information", response = DataResponse.class)
    @PostMapping("/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> createNewAddress(@RequestBody Address address) {
        return DataResponse.success(
                "Create new address",
                addressService.insert(address)
        );
    }

    @ApiOperation(value = "Update address information", response = BaseResponse.class)
    @PutMapping("/address/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateAddress(@PathVariable("id") long addressId, @RequestBody Address address) {
        addressService.update(address, addressId);
        return DataResponse.success("Update address");
    }

    @ApiOperation(value = "Remove address", response = BaseResponse.class)
    @DeleteMapping("/address/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> deleteAddress(@PathVariable("id") long addressId) {
        addressService.delete(addressId);
        return DataResponse.success("Delete address");
    }
}
