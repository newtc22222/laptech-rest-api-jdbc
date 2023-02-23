package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.dto.SortOptionDTO;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
public class BannerFilter extends SortOptionDTO {
    public BannerFilter(String sortBy, String sortDir) {
        super(sortBy, sortDir);
    }
}
