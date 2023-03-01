package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class CategoryFilter extends BaseFilter {
    private String name;
    private String image;
    private String description;

    public CategoryFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                          LocalDate deletedDate, Boolean isDel, String updateBy, String name, String image,
                          String description) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public CategoryFilter(Map<String, String> params) {
        super(params);
        this.name = params.get("name");
        this.image = params.get("image");
        this.description = params.get("description");
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.name);
        objects.add(this.image);
        objects.add(this.description);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
