package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.CategoryFilter;
import com.laptech.restapi.model.Category;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
public interface CategoryDAO extends BaseDAO<Category, CategoryFilter, Long> {
}
