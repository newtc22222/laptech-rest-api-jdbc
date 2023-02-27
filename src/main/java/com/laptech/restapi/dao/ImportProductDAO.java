package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.ImportProductFilter;
import com.laptech.restapi.model.ImportProduct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ImportProductDAO extends BaseDAO<ImportProduct, ImportProductFilter, String> {
    Collection<ImportProduct> findImportProductByProductId(String productId);

    Collection<ImportProduct> findImportProductByDate(LocalDate date);

    Collection<ImportProduct> findImportProductByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
