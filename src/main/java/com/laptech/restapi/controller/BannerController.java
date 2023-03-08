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

import javax.validation.Valid;
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
    public ResponseEntity<DataResponse> getBannerWithFilter(@RequestParam(required = false) String startDate,
                                                            @RequestParam(required = false) String endDate,
                                                            @RequestParam(required = false) String date,
                                                            @RequestParam(required = false) String path,
                                                            @RequestParam(required = false) String type, // can upgrade to String[]
                                                            @RequestParam(required = false) String title,
                                                            @RequestParam(required = false) String linkProduct,
                                                            @RequestParam(required = false) String usedDate,
                                                            @RequestParam(required = false) String endedDate,
                                                            @RequestParam(required = false) String createdDate,
                                                            @RequestParam(required = false) String modifiedDate,
                                                            @RequestParam(required = false) String deletedDate,
                                                            @RequestParam(required = false) Boolean isDel,
                                                            @RequestParam(required = false) String updateBy,
                                                            @RequestParam(required = false) String sortBy,
                                                            @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("date", date);
        params.put("path", path);
        params.put("type", type);
        params.put("title", title);
        params.put("linkProduct", linkProduct);
        params.put("usedDate", usedDate);
        params.put("endedDate", endedDate);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);

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
    public ResponseEntity<DataResponse> insertBanner(@Valid @RequestBody BannerDTO bannerDTO) {
        return DataResponse.success(
                "Create new banner",
                bannerService.insert(BannerDTO.transform(bannerDTO))
        );
    }

    @ApiOperation(value = "Change banner information", response = BaseResponse.class)
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateBanner(@PathVariable("id") long bannerId,
                                                     @Valid @RequestBody BannerDTO bannerDTO) {
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
