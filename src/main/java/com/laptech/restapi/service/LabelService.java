package com.laptech.restapi.service;

import com.laptech.restapi.model.Label;

import java.util.Collection;

/**
 * @since 2023-02-06
 */
public interface LabelService extends BaseService<Label, Long> {
    Collection<Label> getLabelsOfProduct(String productId);
}
