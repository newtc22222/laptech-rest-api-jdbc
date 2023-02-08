package com.laptech.restapi.dao;

import com.laptech.restapi.model.Label;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
public interface LabelDAO extends BaseDAO<Label, Long> {
    List<Label> findLabelByProductId(String productId);

    List<Label> findLabelByName(String name);

    List<Label> findLabelByTitle(String title);
}
