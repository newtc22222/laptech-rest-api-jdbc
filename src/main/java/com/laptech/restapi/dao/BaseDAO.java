package com.laptech.restapi.dao;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.dto.SortOptionDTO;

import java.util.Collection;

/**
 * C-U-D, restore, count, count_with_filter, find all, find_with_filter, find_by_id
 *
 * @author Nhat Phi
 * @since 2023-01-30
 */
public interface BaseDAO<T, F extends SortOptionDTO, ID> {
    ID insert(T t); // create -> return id

    int update(T t);

    int delete(ID id, String updateBy);

    long count();

    long countWithFilter(F filter);

    boolean isExists(T t);

    Collection<T> findAll(PagingOptionDTO pagingOption);

    Collection<T> findWithFilter(F filter);

    T findById(ID id);
}
