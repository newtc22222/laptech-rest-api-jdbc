package com.laptech.restapi.service.impl;

import com.laptech.restapi.dao.LogSystemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nhat Phi
 * @since 2023-02-28
 */
@Service
public class LogSystemServiceImpl {
    @Autowired
    private LogSystemDAO logSystemDAO;
}
