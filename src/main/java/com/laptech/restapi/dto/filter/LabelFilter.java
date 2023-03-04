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
public class LabelFilter extends BaseFilter {
    private String name;
    private String icon;
    private String title;

    public LabelFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                       LocalDate deletedDate, Boolean isDel, String updateBy, String name, String icon, String title) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.name = name;
        this.icon = icon;
        this.title = title;
    }

    public LabelFilter(Map<String, String> params) {
        super(params);
        this.name = params.get("name");
        this.icon = params.get("icon");
        this.title = params.get("title");
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.name);
        objects.add(this.icon);
        objects.add(this.title);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
