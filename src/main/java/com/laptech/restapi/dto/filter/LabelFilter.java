package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class LabelFilter extends BaseFilter{
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
}
