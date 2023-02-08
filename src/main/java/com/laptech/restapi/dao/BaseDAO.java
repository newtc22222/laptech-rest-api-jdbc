package com.laptech.restapi.dao;

import java.util.List;

/**
 * C-U-D, count, find all, find all with limit and offset, find one
 *
 * @author Nhat Phi
 * @since 2023-01-30
 */
public interface BaseDAO<T, ID> {
    ID insert(T t); // create -> return id

    int update(T t);

    int delete(ID id);

    int count();

    boolean isExists(T t);

    List<T> findAll();

    List<T> findAll(long limit, long skip);

    T findById(ID id);
}
