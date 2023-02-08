package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Banner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Nhat Phi
 * @version 1.0.2
 * @since 2022-11-25
 */
@Getter
@Setter
@ApiModel("Class representing for Banner request body")
public class BannerDTO {
    private String path;
    private String type;
    @ApiModelProperty(example = "2022-12-22")
    private String usedDate;
    @ApiModelProperty(example = "2022-12-22")
    private String endedDate;

    public static Banner transform(BannerDTO bannerDTO) {
        Banner banner = new Banner();
        banner.setId(0L);
        banner.setPath(bannerDTO.getPath());
        banner.setType(bannerDTO.getType());
        banner.setUsedDate(LocalDate.parse(bannerDTO.getUsedDate()));
        banner.setEndedDate(LocalDate.parse(bannerDTO.getEndedDate()));
        return banner;
    }
}

