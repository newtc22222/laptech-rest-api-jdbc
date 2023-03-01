package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.CategoryDAO;
import com.laptech.restapi.dto.filter.CategoryFilter;
import com.laptech.restapi.model.Category;
import com.laptech.restapi.service.CategoryService;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public Category insert(Category category) {
        // check

        if (categoryDAO.isExists(category)) {
            throw new ResourceAlreadyExistsException("[Info] This category has already existed in system!");
        }

        Long newCategoryId = categoryDAO.insert(category);
        if (newCategoryId == null) {
            throw new InternalServerErrorException("[Error] Failed insert new category!");
        }
        return categoryDAO.findById(newCategoryId);
    }

    @Override
    public void update(Category category, Long categoryId) {
        // check

        Category oldCategory = categoryDAO.findById(categoryId);
        if (oldCategory == null) {
            throw new ResourceNotFoundException("[Info] Cannot find category with id=" + categoryId);
        } else {
            oldCategory.setName(category.getName());
            oldCategory.setImage(category.getImage());
            oldCategory.setDescription(category.getDescription());
            oldCategory.setUpdateBy(category.getUpdateBy());

            if (categoryDAO.update(oldCategory) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this category!");
            }
        }
    }

    @Override
    public void delete(Long categoryId, String updateBy) {
        if (categoryDAO.findById(categoryId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find category with id=" + categoryId);
        } else {
            if (categoryDAO.delete(categoryId, updateBy) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this category!");
        }
    }

    @Override
    public long count() {
        return categoryDAO.count();
    }

    @Override
    public Collection<Category> findAll(String sortBy, String sortDir, Long page, Long size) {
        return categoryDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Category> findWithFilter(Map<String, Object> params) {
        CategoryFilter filter = new CategoryFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return categoryDAO.findWithFilter(filter);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryDAO.findById(categoryId);
    }
}
