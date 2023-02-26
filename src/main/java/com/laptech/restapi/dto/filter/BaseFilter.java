package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.dto.SortOptionDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter @Setter
public abstract class BaseFilter extends SortOptionDTO {
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private LocalDate deletedDate;
    private Boolean isDel;
    private String updateBy;

    public BaseFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate, LocalDate deletedDate, Boolean isDel, String updateBy) {
        super(sortBy, sortDir);
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deletedDate = deletedDate;
        this.isDel = isDel;
        this.updateBy = updateBy;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.createdDate);
        objects.add(this.modifiedDate);
        objects.add(this.deletedDate);
        objects.add(this.isDel);
        objects.add(this.updateBy);
        if(hasSort) {
            objects.add(super.getSortBy());
            objects.add(super.getSortDir());
        }
        return objects.toArray();
    }
}
