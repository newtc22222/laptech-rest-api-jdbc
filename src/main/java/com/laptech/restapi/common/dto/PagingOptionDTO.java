package com.laptech.restapi.common.dto;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
public class PagingOptionDTO extends SortOptionDTO {
    private Long offset;
    private Long count;

    public PagingOptionDTO(String sortBy, String sortDir, Long page, Long size) {
        super(sortBy, sortDir);
        if(page != null && size != null) {
            this.offset = (page - 1) * size;
            this.count = size;
        }
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}