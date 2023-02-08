package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.CategoryDAO;
import com.laptech.restapi.model.Category;
import com.laptech.restapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

            if (categoryDAO.update(oldCategory) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this category!");
            }
        }
    }

    @Override
    public void delete(Long categoryId) {
        if (categoryDAO.findById(categoryId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find category with id=" + categoryId);
        } else {
            if (categoryDAO.delete(categoryId) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this category!");
        }
    }

    @Override
    public List<Category> findAll(Long page, Long size) {
        if (size == null)
            return categoryDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return categoryDAO.findAll(limit, skip);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryDAO.findById(categoryId);
    }
}
