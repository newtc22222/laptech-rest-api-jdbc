package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.BannerDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Banner;
import com.laptech.restapi.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Api(tags = "Big image for advertise", value = "Banner Controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/banners")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "Get all banners in system", response = Banner.class)
    @GetMapping("")
    public ResponseEntity<Collection<Banner>> getBanners(@RequestParam(required = false, defaultValue = "1") Long page,
                                                         @RequestParam(required = false) Long size,
                                                         @RequestParam(value = "startDate", required = false) String startDate,
                                                         @RequestParam(value = "endDate", required = false) String endDate,
                                                         @RequestParam(value = "date", required = false) String date,
                                                         @RequestParam(value = "type", required = false) String type) {
        if (startDate == null && endDate == null && date == null && type == null) {
            return ResponseEntity.ok(bannerService.findAll(page, size));
        }

        Map<String, String> params = new HashMap<>();
        if (startDate != null) params.put("startDate", startDate);
        if (endDate != null) params.put("endDate", endDate);
        if (date != null) params.put("date", date);
        if (type != null) params.put("type", type);
        return ResponseEntity.ok(bannerService.filter(params));
    }

    @ApiOperation(value = "Get banner by bannerId", response = Banner.class)
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Banner> findBannerById(@PathVariable("id") long bannerId) {
        return ResponseEntity.ok(bannerService.findById(bannerId));
    }

    @ApiOperation(value = "Create new banner", response = DataResponse.class)
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> insertBanner(@RequestBody BannerDTO bannerDTO) {
        return DataResponse.success(
                "Create new banner",
                bannerService.insert(BannerDTO.transform(bannerDTO))
        );
    }

    @ApiOperation(value = "Change banner information", response = BaseResponse.class)
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateBanner(@PathVariable("id") long bannerId,
                                                     @RequestBody BannerDTO bannerDTO) {
        bannerService.update(BannerDTO.transform(bannerDTO), bannerId);
        return DataResponse.success("Update banner");
    }

    @ApiOperation(value = "Delete banner", response = BaseResponse.class)
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteBanner(@PathVariable("id") long bannerId) {
        bannerService.delete(bannerId);
        return DataResponse.success("Delete banner");
    }
}
