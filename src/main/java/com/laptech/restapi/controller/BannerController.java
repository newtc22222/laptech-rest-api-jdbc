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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public ResponseEntity<DataResponse> getAllBanner(@RequestParam(required = false) String sortBy,
                                                     @RequestParam(required = false) String sortDir,
                                                     @RequestParam(required = false) Long page,
                                                     @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all banners",
                bannerService.count(),
                bannerService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get banners with filter", response = Banner.class)
    @GetMapping("filter")
    public ResponseEntity<DataResponse> getBannerWithFilter(@RequestParam(value = "startDate", required = false) String startDate,
                                                            @RequestParam(value = "endDate", required = false) String endDate,
                                                            @RequestParam(value = "date", required = false) String date,
                                                            @RequestParam(value = "type", required = false) String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("date", date);
        params.put("type", type);
        Set<Banner> banners = (Set<Banner>) bannerService.findWithFilter(params);
        return DataResponse.getCollectionSuccess(
                "Get banner with filter",
                banners
        );
    }

    @ApiOperation(value = "Get banner by bannerId", response = Banner.class)
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> findBannerById(@PathVariable("id") long bannerId) {
        return DataResponse.getObjectSuccess(
                "Get banner",
                bannerService.findById(bannerId)
        );
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
    public ResponseEntity<BaseResponse> deleteBanner(@PathVariable("id") long bannerId,
                                                     @RequestBody(required = false) Map<String, String> body) {
        bannerService.delete(bannerId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete banner");
    }
}
