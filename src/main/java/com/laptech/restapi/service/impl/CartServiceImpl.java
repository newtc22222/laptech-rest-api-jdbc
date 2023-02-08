package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.CartDAO;
import com.laptech.restapi.model.Cart;
import com.laptech.restapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDAO cartDAO;

    @Override
    public Cart insert(Cart cart) {
        //check

        if (cartDAO.isExists(cart)) {
            throw new ResourceAlreadyExistsException("[Info] This cart has already existed in system!");
        }
        String newCartId = cartDAO.insert(cart);
        if (newCartId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new cart!");
        }
        return cartDAO.findById(newCartId);
    }

    @Override
    public void update(Cart cart, Long userId) {
        Cart oldCart = cartDAO.findCartByUserId(userId);

        if (oldCart == null) {
            throw new ResourceNotFoundException("[Info] Can not find this cart in system!");
        } else {
            oldCart.setDiscountId(cart.getDiscountId());

            if (cartDAO.update(oldCart) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this cart!");
            }
        }
    }

    @Override
    public void delete(Long userId) {
        Cart existsCart = cartDAO.findCartByUserId(userId);
        if (existsCart == null) {
            throw new ResourceNotFoundException("[Info] Can not find this cart in system!");
        } else {
            if (cartDAO.delete(existsCart.getId()) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove cart!");
            }
        }
    }

    @Override
    public List<Cart> findAll(Long page, Long size) {
        return null;
    }

    /**
     * Actually, this function find with userId
     */
    @Override
    public Cart findById(Long userId) {
        return cartDAO.findCartByUserId(userId);
    }
}
