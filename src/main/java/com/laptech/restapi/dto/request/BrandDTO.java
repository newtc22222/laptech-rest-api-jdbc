package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Brand;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@ApiOperation("")
public class BrandDTO {
    private String name;
    private String country;
    private String establishDate;
    private String logo;

    public static Brand transform(Map<String, String> request) {
        Brand brand = new Brand();
        brand.setId(0L);
        brand.setName(request.get("name"));
        brand.setCountry(request.get("country"));
        if (request.containsKey("establishDate")) {
            brand.setEstablishDate(LocalDate.parse(request.get("establishDate")));
        }
        brand.setLogo(request.get("logo"));
        return brand;
    }
}
