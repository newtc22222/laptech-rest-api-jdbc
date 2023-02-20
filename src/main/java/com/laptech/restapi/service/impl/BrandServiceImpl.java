package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.BrandDAO;
import com.laptech.restapi.model.Brand;
import com.laptech.restapi.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-18
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDAO brandDAO;

    @Override
    public Brand insert(Brand brand) {
        // check

        if (brandDAO.isExists(brand)) {
            throw new ResourceAlreadyExistsException("Your input brand had already exists in system!");
        }
        Long newBrandId = brandDAO.insert(brand);
        if (newBrandId == null) {
            throw new InternalServerErrorException("Cannot insert new brand to database!");
        }
        return brandDAO.findById(newBrandId);
    }

    @Override
    public void update(Brand brand, Long brandId) {
        // check

        Brand oldBrand = brandDAO.findById(brandId);
        if (oldBrand == null) {
            throw new ResourceNotFoundException("cannot find brand with id=" + brandId);
        } else {
            oldBrand.setName(brand.getName());
            oldBrand.setCountry(brand.getCountry());
            oldBrand.setLogo(brand.getLogo());
            oldBrand.setEstablishDate(brand.getEstablishDate());

            if (brandDAO.update(oldBrand) == 0) {
                throw new InternalServerErrorException("Can not update this brand!");
            }
        }
    }

    @Override
    public void delete(Long brandId) {
        if (brandDAO.findById(brandId) == null) {
            throw new ResourceNotFoundException("cannot find brand with id=" + brandId);
        } else {
            brandDAO.delete(brandId);
        }
    }

    @Override
    public List<Brand> findAll(Long page, Long size) {
        if (size == null)
            return brandDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return brandDAO.findAll(limit, skip);
    }

    @Override
    public Brand findById(Long brandId) {
        Brand brand = brandDAO.findById(brandId);
        if (brand == null) {
            throw new ResourceNotFoundException("cannot find brand with id=" + brandId);
        }
        return brand;
    }
}
