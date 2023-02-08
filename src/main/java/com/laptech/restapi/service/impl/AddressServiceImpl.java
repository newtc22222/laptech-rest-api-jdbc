package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.InvalidArgumentException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.AddressDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.model.Address;
import com.laptech.restapi.service.AddressService;
import com.laptech.restapi.audit.AddressAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        String invalidArguments = AddressAudit.getInvalidArguments(address);
        if (invalidArguments != null) {
            throw new InvalidArgumentException(invalidArguments);
        }

        if (addressDAO.isExists(address)) {
            throw new ResourceAlreadyExistsException("[Info] This address has already existed in system!");
        }

        Long newAddressId = addressDAO.insert(address);
        if (newAddressId == null)
            throw new InternalServerErrorException("[Error] Failed to insert new address!");

        return addressDAO.findById(newAddressId);
    }

    @Override
    public void update(Address address, Long addressId) {
        String invalidArguments = AddressAudit.getInvalidArguments(address);
        if (invalidArguments != null) {
            throw new InvalidArgumentException(invalidArguments);
        }

        Address oldAddress = addressDAO.findById(addressId);
        if (oldAddress == null) {
            throw new ResourceNotFoundException("[Info] Cannot find address with id=" + addressId);
        } else {
            oldAddress.setCountry(address.getCountry());
            oldAddress.setLine1(address.getLine1());
            oldAddress.setLine2(address.getLine2());
            oldAddress.setLine3(address.getLine3());
            oldAddress.setStreet(address.getStreet());

            if (addressDAO.update(oldAddress) == 0) {
                throw new InternalServerErrorException("[Error] Fail to update this address!");
            }
        }
    }

    @Override
    public void delete(Long addressId) {
        if (addressDAO.findById(addressId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot found address with id=" + addressId);
        } else {
            if (addressDAO.delete(addressId) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this address!");
        }
    }

    @Override
    public List<Address> findAll(Long page, Long size) {
        if (size == null)
            return addressDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return addressDAO.findAll(limit, skip);
    }

    @Override
    public Address findById(Long addressId) {
        Address address = addressDAO.findById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException("[Info] Cannot found address with id=" + addressId);
        }
        return address;
    }

    @Override
    public List<Address> findAddressByUserId(long userId) {
        if (userDAO.findById(userId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        return addressDAO.findAddressByUserId(userId);
    }
}
