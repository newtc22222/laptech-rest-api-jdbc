package com.laptech.restapi.service;

import com.laptech.restapi.model.Address;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface AddressService extends BaseService<Address, String> {
    Collection<Address> findAddressByUserId(long userId);
}
