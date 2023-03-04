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
public class RoleFilter extends BaseFilter {
    private String name;
    private String description;

    public RoleFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                      LocalDate deletedDate, Boolean isDel, String updateBy, String name, String description) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.name = name;
        this.description = description;
    }

    public RoleFilter(Map<String, String> params) {
        super(params);
        this.name = params.get("name");
        this.description = params.get("description");
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.name);
        objects.add(this.description);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
