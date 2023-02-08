package com.laptech.restapi.service;

import java.util.List;

/**
 * C-U-D, get all, get one
 *
 * @param <T>  datatype of object
 * @param <ID> datatype of object's index
 * @author Nhat Phi
 * @since 2023-01-30
 */

public interface BaseService<T, ID> {
    /**
     * @param t object
     * @return t with new id was created in database
     */
    T insert(T t);

    void update(T t, ID id);

    void delete(ID id);

    List<T> findAll(Long page, Long size);

    T findById(ID id);
}
