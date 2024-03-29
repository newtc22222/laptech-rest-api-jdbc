package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.CartDAO;
import com.laptech.restapi.model.Cart;
import com.laptech.restapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDAO cartDAO;

    @Override
    public Cart insert(Cart cart) {
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
            oldCart.setUpdateBy(cart.getUpdateBy());

            if (cartDAO.update(oldCart) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this cart!");
            }
        }
    }

    @Override
    public void delete(Long userId, String updateBy) {
        Cart existsCart = cartDAO.findCartByUserId(userId);
        if (existsCart == null) {
            throw new ResourceNotFoundException("[Info] Can not find this cart in system!");
        } else {
            if (cartDAO.delete(existsCart.getId(), updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove cart!");
            }
        }
    }

    @Override
    public long count() {
        return cartDAO.count();
    }

    @Override
    public Collection<Cart> findAll(String sortBy, String sortDir, Long page, Long size) {
        return null;
    }

    @Override
    public Collection<Cart> findWithFilter(Map<String, Object> params) {
        return null;
    }

    /**
     * Actually, this function find with userId
     */
    @Override
    public Cart findById(Long userId) {
        Cart currentCart = cartDAO.findCartByUserId(userId);
        if (currentCart == null) {
            currentCart = new Cart();
            currentCart.setId(UUID.randomUUID().toString().replace("-", ""));
            currentCart.setUserId(userId);
            currentCart.setUpdateBy("SYSTEM");
            cartDAO.insert(currentCart);
        }
        return currentCart;
    }
}
