package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.ImportProductFilter;
import com.laptech.restapi.model.ImportProduct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ImportProductDAO extends BaseDAO<ImportProduct, ImportProductFilter, String> {
    List<ImportProduct> findImportProductByProductId(String productId);

    List<ImportProduct> findImportProductByDate(LocalDate date);

    List<ImportProduct> findImportProductByDateRange(LocalDateTime startDate, LocalDateTime endDate);

//    List<ImportProduct> findImportProductTicketByProductIdAndDate(String productId, LocalDate date);
//    List<ImportProduct> findImportProductTicketByProductIdAndDateRange(String productId, LocalDateTime startDate, LocalDateTime endDate);
}
