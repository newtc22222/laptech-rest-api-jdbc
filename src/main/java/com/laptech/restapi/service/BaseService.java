package com.laptech.restapi.service;

import java.util.Collection;
import java.util.Map;

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

    void delete(ID id, String updateBy);

    long count();

    Collection<T> findAll(String sortBy, String sortDir, Long page, Long size);

    default Collection<T> findAll() {
        return this.findAll(null, null, null, null);
    }

    Collection<T> findWithFilter(Map<String, Object> params);

    T findById(ID id);
}
