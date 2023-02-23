package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.BannerFilter;
import com.laptech.restapi.model.Banner;

import java.sql.Date;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
public interface BannerDAO extends BaseDAO<Banner, BannerFilter, Long> {
    List<Banner> findBannerByDateRange(Date startDate, Date endDate);

    List<Banner> findBannerByDate(Date date);
}
