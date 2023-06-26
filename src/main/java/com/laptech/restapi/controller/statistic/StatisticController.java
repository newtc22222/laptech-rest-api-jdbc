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
        if (date != null) {
            filterDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        data.put("billInDay", statisticService.getBillInDay(filterDate));
        data.put("incomeInDay", statisticService.getIncomeInDay(filterDate));
        data.put("accessInDay", statisticService.getAccessInDay(filterDate));
        data.put("productOutOfStock", statisticService.getListProductOutOfStock());
        return data;
    }

    @GetMapping("/products")
    public Map<String, Object> getFigureOfAllProducts(@RequestParam(required = false) Long brandId,
                                                      @RequestParam(required = false) Long categoryId,
                                                      @RequestParam(required = false) Long limit) {
        Map<String, Object> figures = new HashMap<>();
        figures.put("topProductSeller", statisticService.getTopProductSeller(brandId, categoryId, limit));
        figures.put("topProductImport", statisticService.getTopProductImport(brandId, categoryId, limit));
        figures.put("productListOutOfStock", statisticService.getListProductOutOfStock());
        return figures;
    }

    @GetMapping("/products/{id}")
    public Map<String, Object> getFigureOfProduct(@PathVariable(value = "id") String productId,
                                                  @RequestParam(defaultValue = "month", required = false) String range) {
        Map<String, Object> figures = new HashMap<>();
        figures.put("rating", statisticService.getProductRating(productId));
        figures.put("topUserFollow", statisticService.getTopUserFollower(productId));
        if (range.equals("month")) {
            figures.put("priceFigures", statisticService.getPriceInMonth(productId));
            figures.put("incomeFigures", statisticService.getIncomeInMonth(productId));
            figures.put("payingFigures", statisticService.getPayingInMonth(productId));
        } else {
            figures.put("priceFigures", statisticService.getPriceInYear(productId));
            figures.put("incomeFigures", statisticService.getIncomeInYear(productId));
            figures.put("payingFigures", statisticService.getPayingInYear(productId));
        }
        return figures;
    }

    @GetMapping("/profits")
    public Map<String, Object> getFigureProfits(@RequestParam(defaultValue = "month", required = false) String range) {
        Map<String, Object> figures = new HashMap<>();
        if (range.equals("month")) {
            figures.put("incomeFigures", statisticService.getIncomeInMonth(null));
            figures.put("payingFigures", statisticService.getPayingInMonth(null));
        } else {
            figures.put("incomeFigures", statisticService.getIncomeInYear(null));
            figures.put("payingFigures", statisticService.getPayingInYear(null));
        }
        return figures;
    }

    @GetMapping("/users")
    public Map<String, Object> getFigureOfAllUsers(@RequestParam(required = false) LocalDate dateFilter,
                                                   @RequestParam(defaultValue = "month", required = false) String range) {
        Map<String, Object> figures = new HashMap<>();
        figures.put("topUserValue", statisticService.getTopUserValue(dateFilter));
        figures.put("topUserAccess", statisticService.getTopUserAccess(dateFilter));
        if (range.equals("month")) {
            figures.put("accessFigures", statisticService.getAccessInMonth());
            figures.put("interactFigures", statisticService.getInteractInMonth());
        } else {
            figures.put("accessFigures", statisticService.getAccessInYear());
            figures.put("interactFigures", statisticService.getInteractInYear());
        }
        return figures;
    }
}
