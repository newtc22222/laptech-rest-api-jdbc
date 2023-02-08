package com.laptech.restapi.dao;

import com.laptech.restapi.model.ImportProduct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ImportProductDAO extends BaseDAO<ImportProduct, String> {
    List<ImportProduct> findImportProductTicketByProductId(String productId);

    List<ImportProduct> findImportProductTicketByDate(LocalDate date);

    List<ImportProduct> findImportProductTicketByDateRange(LocalDateTime startDate, LocalDateTime endDate);

//    List<ImportProduct> findImportProductTicketByProductIdAndDate(String productId, LocalDate date);
//    List<ImportProduct> findImportProductTicketByProductIdAndDateRange(String productId, LocalDateTime startDate, LocalDateTime endDate);
}
