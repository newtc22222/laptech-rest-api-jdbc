package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.BrandDAO;
import com.laptech.restapi.dto.filter.BrandFilter;
import com.laptech.restapi.model.Brand;
import com.laptech.restapi.service.BrandService;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

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
            oldBrand.setUpdateBy(brand.getUpdateBy());

            if (brandDAO.update(oldBrand) == 0) {
                throw new InternalServerErrorException("Can not update this brand!");
            }
        }
    }

    @Override
    public void delete(Long brandId, String updateBy) {
        if (brandDAO.findById(brandId) == null) {
            throw new ResourceNotFoundException("cannot find brand with id=" + brandId);
        } else {
            if (brandDAO.delete(brandId, updateBy) == 0) {
                throw new InternalServerErrorException("Can not delete this brand!");
            }
        }
    }

    @Override
    public long count() {
        return brandDAO.count();
    }

    @Override
    public Collection<Brand> findAll(String sortBy, String sortDir, Long page, Long size) {
        return brandDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Brand> findWithFilter(Map<String, Object> params) {
        BrandFilter filter = new BrandFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return brandDAO.findWithFilter(filter);
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
