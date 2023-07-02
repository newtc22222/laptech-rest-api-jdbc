package com.laptech.restapi.common.dto;

import com.laptech.restapi.common.enums.SortDir;

import java.util.Optional;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
public class SortOptionDTO {
    private String sortBy;
    private SortDir sortDir;

    public SortOptionDTO(String sortBy, String sortDir) {
        String sortDirUpperCase = Optional.ofNullable(sortDir).orElse("").toUpperCase();
        this.sortBy = sortBy;
        this.sortDir = sortDirUpperCase.equals("") ? SortDir.ASC : SortDir.valueOf(sortDir);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortDir getSortDir() {
        return sortDir;
    }

    public void setSortDir(SortDir sortDir) {
        this.sortDir = sortDir;
    }
}
