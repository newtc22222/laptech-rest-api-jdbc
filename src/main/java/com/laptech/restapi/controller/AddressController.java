package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.AddressDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Address;
import com.laptech.restapi.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<DataResponse> findAddressById(@PathVariable("id") String addressId) {
        return DataResponse.getObjectSuccess(
                "Get address",
                addressService.findById(addressId)
        );
    }

    @ApiOperation(value = "Get list address of user (userId)", response = Address.class)
    @GetMapping("/users/{userId}/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> getAllAddressOfUser(@PathVariable("userId") long userId,
                                                            @RequestParam(required = false) String country,
                                                            @RequestParam(required = false) String line1,
                                                            @RequestParam(required = false) String line2,
                                                            @RequestParam(required = false) String line3,
                                                            @RequestParam(required = false) String street,
                                                            @RequestParam(required = false) Boolean isDefault) {
        Map<String, Object> params = new HashMap<>();
        params.put("country", country);
        params.put("line1", line1);
        params.put("line2", line2);
        params.put("line3", line3);
        params.put("street", street);
        params.put("isDefault", isDefault);
        if(!params.containsValue(null)){
            params.put("userId", userId);
            return DataResponse.getCollectionSuccess(
                    "Get address of user with filter",
                    addressService.findWithFilter(params)
            );
        }
        return DataResponse.getCollectionSuccess(
                "Get all addresses of user",
                addressService.findAddressByUserId(userId)
        );
    }

    @ApiOperation(value = "Add new address information", response = DataResponse.class)
    @PostMapping("/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> createNewAddress(@Valid @RequestBody AddressDTO dto) {
        return DataResponse.success(
                "Create new address",
                addressService.insert(AddressDTO.transform(dto))
        );
    }

    @ApiOperation(value = "Update address information", response = BaseResponse.class)
    @PutMapping("/address/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateAddress(@PathVariable("id") String addressId,
                                                      @Valid @RequestBody AddressDTO dto) {
        addressService.update(AddressDTO.transform(dto), addressId);
        return DataResponse.success("Update address");
    }

    @ApiOperation(value = "Update default address", response = BaseResponse.class)
    @PatchMapping("/address/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateDefaultAddress(@PathVariable("id") String addressId,
                                                             @RequestBody Map<String, String> body) {
        addressService.setDefaultAddress(
                addressId,
                Long.parseLong(body.get("userId")),
                body.get("updateBy"));
        return DataResponse.success("Set default address");
    }

    @ApiOperation(value = "Remove address", response = BaseResponse.class)
    @DeleteMapping("/address/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> deleteAddress(@PathVariable("id") String addressId,
                                                      @RequestBody(required = false) Map<String, String> body) {
        addressService.delete(addressId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete address");
    }
}
