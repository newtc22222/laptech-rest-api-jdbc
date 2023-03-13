package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.DiscountDAO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dto.filter.DiscountFilter;
import com.laptech.restapi.model.Discount;
import com.laptech.restapi.service.DiscountService;
import com.laptech.restapi.util.ConvertDateTime;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
            oldDiscount.setUpdateBy(discount.getUpdateBy());

            if (discountDAO.update(oldDiscount) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this discount!");
            }
        }
    }

    @Override
    public void delete(Long discountId, String updateBy) {
        if (discountDAO.findById(discountId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find discount with id=" + discountId);
        } else {
            if (discountDAO.delete(discountId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this discount!");
            }
        }
    }

    @Override
    public long count() {
        return discountDAO.count();
    }

    @Override
    public Collection<Discount> findAll(String sortBy, String sortDir, Long page, Long size) {
        return discountDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Discount> findWithFilter(Map<String, Object> params) {
        DiscountFilter filter = new DiscountFilter(ConvertMap.changeAllValueFromObjectToString(params));
        Set<Discount> discountSet = (Set<Discount>) discountDAO.findWithFilter(filter);

        if (params.containsKey("startDate") && params.containsKey("endDate")) {
            Collection<Discount> discountList = discountDAO.findDiscountByDateRange(
                    ConvertDateTime.getLocalDateTimeFromString(params.get("startDate").toString()),
                    ConvertDateTime.getLocalDateTimeFromString(params.get("endDate").toString())
            );
            discountSet.removeIf(item -> !discountList.contains(item));
        }
        return discountSet;
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
    public Collection<Discount> getDiscountsOfProduct(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return discountDAO.findDiscountByProductId(productId);
    }
}
