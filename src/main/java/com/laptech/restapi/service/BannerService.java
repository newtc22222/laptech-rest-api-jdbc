package com.laptech.restapi.service;

import com.laptech.restapi.model.Banner;

import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface BannerService extends BaseService<Banner, Long> {
    Set<Banner> filter(Map<String, String> params);
}
