package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.AddressDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.model.Address;
import com.laptech.restapi.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Address insert(Address address) {
        if (addressDAO.isExists(address)) {
            throw new ResourceAlreadyExistsException("[Info] This address has already existed in system!");
        }

        String newAddressId = addressDAO.insert(address);
        if (newAddressId == null)
            throw new InternalServerErrorException("[Error] Failed to insert new address!");

        return addressDAO.findById(newAddressId);
    }

    @Override
    public void update(Address address, String addressId) {
        Address oldAddress = addressDAO.findById(addressId);
        if (oldAddress == null) {
            throw new ResourceNotFoundException("[Info] Cannot find address with id=" + addressId);
        } else {
            oldAddress.setCountry(address.getCountry());
            oldAddress.setLine1(address.getLine1());
            oldAddress.setLine2(address.getLine2());
            oldAddress.setLine3(address.getLine3());
            oldAddress.setStreet(address.getStreet());
            oldAddress.setDefault(address.isDefault());
            oldAddress.setUpdateBy(address.getUpdateBy());

            if (addressDAO.update(oldAddress) == 0) {
                throw new InternalServerErrorException("[Error] Fail to update this address!");
            }
        }
    }

    @Override
    public void delete(String addressId, String updateBy) {
        if (addressDAO.findById(addressId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot found address with id=" + addressId);
        } else {
            if (addressDAO.delete(addressId, updateBy) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this address!");
        }
    }

    @Override
    public long count() {
        return addressDAO.count();
    }

    @Override
    public Collection<Address> findAll(String sortBy, String sortDir, Long page, Long size) {
        return addressDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Address> findWithFilter(Map<String, Object> params) {
        return addressDAO.findAll();
    }

    @Override
    public Address findById(String addressId) {
        Address address = addressDAO.findById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException("[Info] Cannot found address with id=" + addressId);
        }
        return address;
    }

    @Override
    public void setDefaultAddress(String addressId, long userId, String updateBy) {
        if (addressDAO.setDefaultAddress(addressId, userId, updateBy) == 0) {
            throw new InternalServerErrorException("[Error] Fail to set default for this address!");
        }
    }

    @Override
    public Collection<Address> findAddressByUserId(long userId) {
        if (userDAO.findById(userId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        return addressDAO.findAddressByUserId(userId);
    }
}
