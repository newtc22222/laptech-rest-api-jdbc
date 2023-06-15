package com.laptech.restapi.controller.statistic;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.ProductUnit;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @since 2023-06-01
 */
@Api(tags = "Statistic API", value = "Statistic Controller")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/statistic")
public class StatisticController {
    private final InvoiceService invoiceService;
    private final CommentService commentService;
    private final FeedbackService feedbackService;
    private final ProductService productService;
    private final ProductUnitService productUnitService;

    private final LogSystemService logSystemService;
    private final RefreshTokenService refreshTokenService;
    private final RoleService roleService;
    private final UserService userService;

    @GetMapping("dashboard")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public Map<String, Object> getDashboardStatistic() {
        Map<String, Object> data = new HashMap<>();
        long billInDay = invoiceService.findAll()
                .stream()
                .filter(invoice -> invoice.getCreatedDate().toLocalDate().isEqual(LocalDate.now()))
                .count();
        BigDecimal incomeInDay = invoiceService.findAll()
                .stream()
                .filter(invoice -> invoice.getModifiedDate().toLocalDate().isEqual(LocalDate.now()))
                .filter(invoice -> invoice.getOrderStatus().equals(OrderStatus.RECEIVED))
                .map(Invoice::getPaymentTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long accessInDay = refreshTokenService.findAll()
                .stream()
                .filter(refreshToken -> refreshToken.getExpiredDate().toLocalDate().isAfter(LocalDate.now()))
                .count();
        Collection<Product> productOutOfStock = productService.findAll()
                .stream()
                .filter(product -> product.getQuantityInStock() == 0)
                .collect(Collectors.toList());

        data.put("billInDay", billInDay);
        data.put("incomeInDay", incomeInDay);
        data.put("accessInDay", accessInDay);
        data.put("productOutOfStock", productOutOfStock);
        return data;
    }

    @GetMapping("/products")
    public Map<String, Object> getFigureOfAllProducts(@RequestParam(required = false) String date,
                                                      @RequestParam(required = false) List<Long> brandIdList,
                                                      @RequestParam(required = false) List<Long> categoryIdList) {
        // Get all items (from all invoices in month)
        List<Invoice> invoicesInMonth = invoiceService.findAll()
                .stream()
                .filter(invoice -> invoice.getOrderStatus() == OrderStatus.RECEIVED)
                .filter(invoice
                        -> invoice.getCreatedDate().getMonth().equals(LocalDate.now().getMonth())
                        && invoice.getCreatedDate().getYear() == LocalDate.now().getYear())
                .collect(Collectors.toList());
        Collection<ProductUnit> productUnits = new ArrayList<>();
        invoicesInMonth.forEach(invoice -> {
            productUnits.addAll(productUnitService.getProductUnitByInvoiceId(invoice.getId()));
        });

        // Flatten items -> products list (allow duplicate)
        List<Product> products = productUnits.stream()
                .map(unit -> productService.findById(unit.getProductId()))
                .collect(Collectors.toList());

        // Calculate frequencies
        Map<Product, Integer> productFrequencies = new HashMap<>();
        for (Product product : products) {
            productFrequencies.put(product, productFrequencies.getOrDefault(product, 0) + 1);
        }

        // Sort
        List<Map.Entry<Product, Integer>> sortedProducts = new ArrayList<>(productFrequencies.entrySet());
        sortedProducts.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        // Response top product: [{ product, quantity}]
        List<Map<String, Object>> topProductResponse = new ArrayList<>();
        sortedProducts.stream().limit(10)
                .forEach(entry -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("product", entry.getKey());
                    response.put("quantity", entry.getValue());
                    topProductResponse.add(response);
                });

        Map<String, Object> figures = new HashMap<>();
        figures.put("topProduct", topProductResponse);
        return figures;
    }

    @GetMapping("/products/{id}")
    public Map<String, Object> getFigureOfProduct(@PathVariable(value = "id") String productId,
                                                  @RequestParam(required = false) String date,
                                                  @RequestParam(required = false) List<Long> brandIdList,
                                                  @RequestParam(required = false) List<Long> categoryIdList) {

        List<User> topUserFollow = null;
        Map<Integer, BigDecimal> priceFigures = null;
        Map<Byte, Integer> ratingFigures = null;

        Map<String, Object> figures = new HashMap<>();
        return figures;
    }

    @GetMapping("/users")
    public Map<String, Object> getFigureOfAllUsers(@RequestParam(required = false) String date) {
        Map<User, BigDecimal> topUserValue = null;
        Map<User, Long> topUserAccess = null;
        Map<Integer, Long> accessInMonth = null;

        Map<String, Object> figures = new HashMap<>();
        return figures;
    }
}
