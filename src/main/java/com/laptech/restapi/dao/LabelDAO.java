package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.LabelFilter;
import com.laptech.restapi.model.Label;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
public interface LabelDAO extends BaseDAO<Label, LabelFilter, Long> {
    Collection<Label> findLabelByProductId(String productId);
}
