package com.laptech.restapi.dao;

import com.laptech.restapi.model.Address;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
public interface AddressDAO extends BaseDAO<Address, Long> {
    List<Address> findAddressByUserId(long userId);
}
