package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.dto.SortOptionDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public LogSystemFilter(String sortBy, String sortDir, String actionTable, LocalDate actionDate, String actionBy, String actionName) {
        super(sortBy, sortDir);
        this.actionTable = actionTable;
        this.actionDate = actionDate;
        this.actionBy = actionBy;
        this.actionName = actionName;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = LocalDate.parse(actionDate, DateTimeFormatter.ISO_DATE);
    }
}
