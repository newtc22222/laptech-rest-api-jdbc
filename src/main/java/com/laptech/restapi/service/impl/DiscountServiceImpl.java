package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.enums.DiscountType;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.DiscountDAO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.model.Discount;
import com.laptech.restapi.service.DiscountService;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountDAO discountDAO;
    @Autowired
    private ProductDAO productDAO;

    @Override
    public Discount insert(Discount discount) {
        // check

        if (discountDAO.isExists(discount)) {
            throw new ResourceAlreadyExistsException("[Info] This discount has already existed in system!");
        }

        Long newDiscountId = discountDAO.insert(discount);
        if (newDiscountId == null) {
            throw new InternalServerErrorException("[Error] Failed insert new discount!");
        }
        return discountDAO.findById(newDiscountId);
    }

    @Override
    public void update(Discount discount, Long discountId) {
        // check

        Discount oldDiscount = discountDAO.findById(discountId);
        if (oldDiscount == null) {
            throw new ResourceNotFoundException("[Info] Cannot find discount with id=" + discountId);
        } else {
            oldDiscount.setCode(discount.getCode());
            oldDiscount.setRate(discount.getRate());
            oldDiscount.setAppliedType(discount.getAppliedType());
            oldDiscount.setMaxAmount(discount.getMaxAmount());
            oldDiscount.setAppliedDate(discount.getAppliedDate());
            oldDiscount.setEndedDate(discount.getEndedDate());

            if (discountDAO.update(discount) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this discount!");
            }
        }
    }

    @Override
    public void delete(Long discountId) {
        if (discountDAO.findById(discountId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find discount with id=" + discountId);
        } else {
            if (discountDAO.delete(discountId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this discount!");
            }
        }
    }

    @Override
    public List<Discount> findAll(Long page, Long size) {
        if (size == null)
            return discountDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return discountDAO.findAll(limit, skip);
    }

    @Override
    public Discount findById(Long discountId) {
        Discount discount = discountDAO.findById(discountId);
        if (discount == null) {
            throw new ResourceNotFoundException("[Info] Cannot find discount with id=" + discountId);
        }
        return discount;
    }

    @Override
    public List<Discount> getDiscountsOfProduct(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return discountDAO.findDiscountByProductId(productId);
    }

    @Override
    public Set<Discount> filter(Map<String, String> params) {
        Set<Discount> discountSet = new HashSet<>(discountDAO.findAll());

        if (params.containsKey("code")) {
            List<Discount> discountList = discountDAO.findDiscountByCode(params.get("code"));
            discountSet.removeIf(item -> !discountList.contains(item));
        }
        if (params.containsKey("startDate") && params.containsKey("endDate")) {
            List<Discount> discountList = discountDAO.findDiscountByDateRange(
                    ConvertDateTime.getDateTimeFromString(params.get("startDate")),
                    ConvertDateTime.getDateTimeFromString(params.get("endDate"))
            );
            discountSet.removeIf(item -> !discountList.contains(item));
        }
        if (params.containsKey("type")) {
            List<Discount> discountList = discountDAO.findDiscountByType(DiscountType.valueOf(params.get("type")));
            discountSet.removeIf(item -> !discountList.contains(item));
        }

        return discountSet;
    }
}
