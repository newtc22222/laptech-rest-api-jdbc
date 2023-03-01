package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.LogSystemDAO;
import com.laptech.restapi.dto.filter.LogSystemFilter;
import com.laptech.restapi.model.LogSystem;
import com.laptech.restapi.service.LogSystemService;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-02-28
 */
@Service
public class LogSystemServiceImpl implements LogSystemService {
    @Autowired
    private LogSystemDAO logSystemDAO;

    @Override
    public Collection<LogSystem> findAll(String sortBy, String sortDir, Long page, Long size) {
        return logSystemDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<LogSystem> findByFilter(Map<String, Object> params) {
        LogSystemFilter filter = new LogSystemFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return logSystemDAO.findByFilter(filter);
    }

    @Override
    public long count() {
        return logSystemDAO.count();
    }
}
