package com.laptech.restapi.service;

import com.laptech.restapi.model.ImportProduct;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface ImportProductService extends BaseService<ImportProduct, String> {
    List<ImportProduct> findImportProductByProductId(String productId);

    /**
     * Filter options:
     * - productId
     * - date | start date + end date
     */
    Set<ImportProduct> filter(Map<String, String> params);
}
