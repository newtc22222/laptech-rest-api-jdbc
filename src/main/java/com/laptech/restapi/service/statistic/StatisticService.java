package com.laptech.restapi.service.statistic;

import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface StatisticService {
    // dash board
    long getBillInDay(LocalDate date);
    BigDecimal getIncomeInDay(LocalDate date);
    long getAccessInDay(LocalDate date);
    Collection<Product> getListProductOutOfStock();

    // products
    List<Map<String, Object>> getTopProductSeller(Long brandId, Long categoryId, Long limit);

    List<Map<String, Object>> getTopProductImport(Long brandId, Long categoryId, Long limit);

    // in month: 30 days, in year: 12 months
    // product (single) + profits
    int[] getProductRating(String productId);
    List<Map<String, Object>> getTopUserFollower(String productId);
    Map<LocalDate, BigDecimal> getPriceInMonth(String productId);
    Map<LocalDate, BigDecimal> getIncomeInMonth(String productId);
    Map<LocalDate, BigDecimal> getPayingInMonth(String productId);
    Map<String, BigDecimal> getPriceInYear(String productId);
    Map<String, BigDecimal> getIncomeInYear(String productId);
    Map<String, BigDecimal> getPayingInYear(String productId);

    // system
    Map<User, BigDecimal> getTopUserValue();
    Map<User, Long> getTopUserAccess();
    Map<LocalDate, Long> getAccessInMonth();
    Map<String, Long> getAccessInYear();
}
