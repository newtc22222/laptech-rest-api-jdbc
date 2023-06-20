package com.laptech.restapi.dao;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dto.filter.LogSystemFilter;
import com.laptech.restapi.model.LogSystem;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2023-02-27
 */
public interface LogSystemDAO {
    Collection<LogSystem> findAll(PagingOptionDTO pagingOption);

    default Collection<LogSystem> findAll() {
        return this.findAll(new PagingOptionDTO(null, null, null, null));
    }

    Collection<LogSystem> findByFilter(LogSystemFilter filter);

    long count();

    long count(LogSystemFilter filter);
}
