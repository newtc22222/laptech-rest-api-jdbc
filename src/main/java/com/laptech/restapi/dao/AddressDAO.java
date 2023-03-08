package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.AddressFilter;
import com.laptech.restapi.model.Address;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
public interface AddressDAO extends BaseDAO<Address, AddressFilter, String> {
    int setDefaultAddress(String addressId, long userId, String updateBy);
    Collection<Address> findAddressByUserId(long userId);
}
