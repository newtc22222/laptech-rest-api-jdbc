package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Banner;
import com.laptech.restapi.util.ConvertDate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Nhat Phi
 * @version 1.0.2
 * @since 2022-11-25
 */
@Getter
@Setter
public class BannerDTO {
    private Long id;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(max = 255, message = "Missing url of images!")
    private String path;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(min = 3, max = 100, message = "You need to import type of this banner!")
    private String type;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(max = 100)
    private String title;
    @Size(max = 255)
    private String linkProduct;
    @ApiModelProperty(required = true, example = "2022-12-22")
    @NotNull
    private String usedDate;
    @ApiModelProperty(required = true, example = "2022-12-22")
    @NotNull
    private String endedDate;
    @Size(max = 100)
    private String updateBy;

    public BannerDTO() {
    }

    public BannerDTO(Long id, String path, String type, String title, String linkProduct,
                     String usedDate, String endedDate, String updateBy) {
        this.id = (id == null) ? 0L : id;
        this.path = path;
        this.type = type;
        this.title = title;
        this.linkProduct = linkProduct;
        this.usedDate = usedDate;
        this.endedDate = endedDate;
        this.updateBy = updateBy;
    }

    public static Banner transform(BannerDTO dto) {
        Banner banner = new Banner();
        banner.setId((dto.getId() != null) ? dto.getId() : 0L);
        banner.setPath(dto.getPath());
        banner.setType(dto.getType());
        banner.setTitle(dto.getTitle());
        banner.setLinkProduct(dto.getLinkProduct());
        banner.setUsedDate(ConvertDate.getLocalDateFromString(dto.getUsedDate()));
        banner.setEndedDate(ConvertDate.getLocalDateFromString(dto.getEndedDate()));
        banner.setUpdateBy(dto.getUpdateBy());
        return banner;
    }
}

