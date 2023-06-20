package com.laptech.restapi.controller.statistic;

import com.laptech.restapi.service.statistic.StatisticService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @since 2023-06-01
 */
@Api(tags = "Statistic API", value = "Statistic Controller")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/statistic")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("dashboard")
    public Map<String, Object> getDashboardStatistic(@RequestParam(required = false) String date) {
        Map<String, Object> data = new HashMap<>();
        LocalDate filterDate = LocalDate.now();
        if (!date.isEmpty()) {
            filterDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        data.put("billInDay", statisticService.getBillInDay(filterDate));
        data.put("incomeInDay", statisticService.getIncomeInDay(filterDate));
        data.put("accessInDay", statisticService.getAccessInDay(filterDate));
        data.put("productOutOfStock", statisticService.getListProductOutOfStock());
        return data;
    }

    @GetMapping("/products")
    public Map<String, Object> getFigureOfAllProducts(@RequestParam(required = false) String date,
                                                      @RequestParam(required = false) Long brandId,
                                                      @RequestParam(required = false) Long categoryId,
                                                      @RequestParam(required = false) Long limit) {
        Map<String, Object> figures = new HashMap<>();
        figures.put("topProductSeller", statisticService.getTopProductSeller(brandId, categoryId, limit));
        figures.put("topProductImport", statisticService.getTopProductImport(brandId, categoryId, limit));
        figures.put("productListOutOfStock", statisticService.getListProductOutOfStock());
        return figures;
    }

    @GetMapping("/products/{id}")
    public Map<String, Object> getFigureOfProduct(@PathVariable(value = "id") String productId) {
        Map<String, Object> figures = new HashMap<>();
        figures.put("rating", statisticService.getProductRating(productId));
        figures.put("priceFigures", statisticService.getPriceInMonth(productId));
        figures.put("incomeInMonth", statisticService.getIncomeInMonth(productId));
        figures.put("payingInMonth", statisticService.getPayingInMonth(productId));
        figures.put("topUserFollow", statisticService.getTopUserFollower(productId));
        return figures;
    }

    @GetMapping("/profits")
    public Map<String, Object> getFigureProfits(@RequestParam(required = false) String date) {
        Map<String, Object> figures = new HashMap<>();
        figures.put("incomeInMonth", statisticService.getIncomeInMonth(null));
        figures.put("payingInMonth", statisticService.getPayingInMonth(null));
        return figures;
    }

    @GetMapping("/users")
    public Map<String, Object> getFigureOfAllUsers() {
        Map<String, Object> figures = new HashMap<>();
        figures.put("topUserValue", statisticService.getTopUserValue());
        figures.put("topUserAccess", statisticService.getTopUserAccess());
        figures.put("accessInMonth", statisticService.getAccessInMonth());
        return figures;
    }
}
