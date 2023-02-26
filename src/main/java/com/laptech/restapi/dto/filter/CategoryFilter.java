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
}
