package com.laptech.restapi.service;

import com.laptech.restapi.model.Address;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface AddressService extends BaseService<Address, Long> {
    List<Address> findAddressByUserId(long userId);
}
