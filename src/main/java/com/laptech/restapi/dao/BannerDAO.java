package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.BannerFilter;
import com.laptech.restapi.model.Banner;

import java.sql.Date;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
public interface BannerDAO extends BaseDAO<Banner, BannerFilter, Long> {
    Collection<Banner> findBannerByDateRange(Date startDate, Date endDate);

    Collection<Banner> findBannerByDate(Date date);
}
