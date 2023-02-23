package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.dto.SortOptionDTO;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
public class AddressFilter extends SortOptionDTO {

    public AddressFilter(String sortBy, String sortDir) {
        super(sortBy, sortDir);
    }
}
