package com.laptech.restapi.service;

import com.laptech.restapi.model.LogSystem;

import java.util.Collection;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-02-28
 */
public interface LogSystemService {
    Collection<LogSystem> findAll(String sortBy, String sortDir, Long page, Long size);

    Collection<LogSystem> findByFilter(Map<String, Object> params);

    long count();
}
