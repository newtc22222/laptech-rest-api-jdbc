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
public class RoleFilter extends BaseFilter {
    private String name;
    private String description;

    public RoleFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                      LocalDate deletedDate, Boolean isDel, String updateBy, String name, String description) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.name = name;
        this.description = description;
    }
}
