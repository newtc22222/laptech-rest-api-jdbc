package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.dto.SortOptionDTO;
import com.laptech.restapi.util.ConvertDate;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
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

    public BaseFilter(Map<String, String> baseParams) {
        super(baseParams.get("sortBy"), baseParams.get("sortDir"));
        this.createdDate = ConvertDate.getLocalDateFromString(baseParams.get("createdDate"));
        this.modifiedDate = ConvertDate.getLocalDateFromString(baseParams.get("modifiedDate"));
        this.deletedDate = ConvertDate.getLocalDateFromString(baseParams.get("deletedDate"));
        this.isDel = baseParams.get("idDel") != null ? Boolean.parseBoolean(baseParams.get("idDel")) : null;
        this.updateBy = baseParams.get("updateBy");
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add((this.createdDate != null) ? Date.valueOf(this.createdDate) : null);
        objects.add((this.modifiedDate != null) ? Date.valueOf(this.modifiedDate) : null);
        objects.add((this.deletedDate != null) ? Date.valueOf(this.deletedDate) : null);
        objects.add(this.isDel);
        objects.add(this.updateBy);
        if (hasSort) {
            objects.add(super.getSortBy());
            objects.add(super.getSortDir().toString());
        }
        return objects.toArray();
    }
}
