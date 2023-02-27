package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter @Setter
public class BrandFilter extends BaseFilter{
    private String name;
    private String country;
    private Year establishYear;
    private String logo;

    public BrandFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate, LocalDate deletedDate,
                       Boolean isDel, String updateBy, String name, String country, Year establishYear, String logo) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.name = name;
        this.country = country;
        this.establishYear = establishYear;
        this.logo = logo;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.name);
        objects.add(this.country);
        objects.add(this.establishYear.toString());
        objects.add(this.logo);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
