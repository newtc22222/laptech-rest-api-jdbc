package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Brand;
import com.laptech.restapi.util.ConvertDate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
public class BrandDTO {
    private Long id;
    @ApiModelProperty(required = true, example = "ASUS")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;
    @ApiModelProperty(required = true, example = "Taiwan")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String country;
    @ApiModelProperty(example = "2000-01-01")
    private String establishDate;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(max = 255)
    private String logo;
    @Size(max = 100)
    private String updateBy;

    public BrandDTO() {
    }

    public BrandDTO(Long id, String name, String country, String establishDate, String logo, String updateBy) {
        this.id = (id == null) ? 0L : id;
        this.name = name;
        this.country = country;
        this.establishDate = establishDate;
        this.logo = logo;
        this.updateBy = updateBy;
    }

    public static Brand transform(BrandDTO dto) {
        Brand brand = new Brand();
        brand.setId((dto.getId() != null) ? dto.getId() : 0L);
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        brand.setEstablishDate(ConvertDate.getLocalDateFromString(dto.getEstablishDate()));
        brand.setLogo(dto.getLogo());
        brand.setUpdateBy(dto.getUpdateBy());
        return brand;
    }
}
