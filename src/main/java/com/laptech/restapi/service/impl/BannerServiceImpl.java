package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.BannerDAO;
import com.laptech.restapi.model.Banner;
import com.laptech.restapi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDAO bannerDAO;

    @Override
    public Banner insert(Banner banner) {
        // check

        if (bannerDAO.isExists(banner)) {
            throw new ResourceAlreadyExistsException("[Info] This banner has already existed in system!");
        }
        Long newBannerId = bannerDAO.insert(banner);
        if (newBannerId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new banner!");
        }
        return bannerDAO.findById(newBannerId);
    }

    @Override
    public void update(Banner banner, Long bannerId) {
        // check

        Banner oldBanner = bannerDAO.findById(bannerId);
        if (oldBanner == null) {
            throw new ResourceNotFoundException("[Info] Cannot find banner with id=" + bannerId);
        } else {
            oldBanner.setPath(banner.getPath());
            oldBanner.setType(banner.getType());
            oldBanner.setTitle(banner.getTitle());
            oldBanner.setLinkProduct(banner.getLinkProduct());
            oldBanner.setUsedDate(banner.getUsedDate());
            oldBanner.setEndedDate(banner.getEndedDate());

            if (bannerDAO.update(oldBanner) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this banner!");
            }
        }
    }

    @Override
    public void delete(Long bannerId) {
        if (bannerDAO.findById(bannerId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find banner with id=" + bannerId);
        } else {
            if (bannerDAO.delete(bannerId) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this banner!");
        }
    }

    @Override
    public List<Banner> findAll(Long page, Long size) {
        if (size == null)
            return bannerDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return bannerDAO.findAll(limit, skip);
    }

    @Override
    public Banner findById(Long bannerId) {
        Banner banner = bannerDAO.findById(bannerId);
        if (banner == null) {
            throw new ResourceNotFoundException("[Info] Cannot find banner with id=" + bannerId);
        }
        return banner;
    }

    @Override
    public Set<Banner> filter(Map<String, String> params) {
        Set<Banner> bannerSet = new HashSet<>(bannerDAO.findAll());

        if (params.containsKey("startDate") && params.containsKey("endDate")) {
            Date startDate = Date.valueOf(params.get("startDate"));
            Date endDate = Date.valueOf(params.get("endDate"));
            List<Banner> bannerList = bannerDAO.findBannerByDateRange(startDate, endDate);
            bannerSet.removeIf(item -> !bannerList.contains(item));
        }
        if (params.containsKey("date")) {
            List<Banner> bannerList = bannerDAO.findBannerByDate(Date.valueOf(params.get("date")));
            bannerSet.removeIf(item -> !bannerList.contains(item));
        }
        if (params.containsKey("type")) {
            List<Banner> bannerList = bannerDAO.findBannerByType(params.get("type"));
            bannerSet.removeIf(item -> !bannerList.contains(item));
        }
        return bannerSet;
    }
}
