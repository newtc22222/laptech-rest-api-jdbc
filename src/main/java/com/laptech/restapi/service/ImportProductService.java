package com.laptech.restapi.service;

import com.laptech.restapi.model.ImportProduct;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface ImportProductService extends BaseService<ImportProduct, String> {
    Collection<ImportProduct> findImportProductByProductId(String productId);
}
