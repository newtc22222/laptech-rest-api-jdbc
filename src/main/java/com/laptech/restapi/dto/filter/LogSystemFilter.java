package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.dto.SortOptionDTO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class LogSystemFilter extends SortOptionDTO {
    private String actionTable;
    private LocalDate actionDate;
    private String actionBy;
    private String actionName;
    private String actionDescription;

    public LogSystemFilter(String sortBy, String sortDir, String actionTable, LocalDate actionDate, String actionBy, String actionName, String actionDescription) {
        super(sortBy, sortDir);
        this.actionTable = actionTable;
        this.actionDate = actionDate;
        this.actionBy = actionBy;
        this.actionName = actionName;
        this.actionDescription = actionDescription;
    }

    public LogSystemFilter(Map<String, String> params) {
        super(params.get("sortBy"), params.get("sortDir"));
        this.actionTable = params.get("actionTable");
        this.setActionDate(params.get("actionDate"));
        this.actionBy = params.get("actionBy");
        this.actionName = params.get("actionName");
        this.actionDescription = params.get("actionDescription");
    }

    public void setActionDate(String actionDate) {
        this.actionDate = LocalDate.parse(actionDate, DateTimeFormatter.ISO_DATE);
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.actionTable);
        objects.add(Date.valueOf(this.actionDate));
        objects.add(this.actionBy);
        objects.add(this.actionName);
        objects.add(this.actionDescription);
        if (hasSort) {
            objects.add(super.getSortBy());
            objects.add(super.getSortDir());
        }
        return objects.toArray();
    }
}
