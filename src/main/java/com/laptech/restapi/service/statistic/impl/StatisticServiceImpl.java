package com.laptech.restapi.service.statistic.impl;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.dao.*;
import com.laptech.restapi.model.*;
import com.laptech.restapi.service.statistic.StatisticService;
import com.laptech.restapi.util.BuildDateTimeCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {
    private final ImportProductDAO importProductDAO;
    private final InvoiceDAO invoiceDAO;
    private final CommentDAO commentDAO;
    private final FeedbackDAO feedbackDAO;
    private final ProductDAO productDAO;
    private final ProductUnitDAO productUnitDAO;

    private final LogSystemDAO logSystemDAO;
    private final RefreshTokenDAO refreshTokenDAO;
    private final RoleDAO roleDAO;
    private final UserDAO userDAO;


    @Override
    public long getBillInDay(LocalDate date) {
        return invoiceDAO
                .findAll()
                .stream()
                .filter(invoice ->
                        invoice.getCreatedDate().toLocalDate().isEqual(date))
                .count();
    }

    @Override
    public BigDecimal getIncomeInDay(LocalDate date) {
        return invoiceDAO
                .findAll()
                .stream()
                .filter(invoice -> invoice.getModifiedDate().toLocalDate().isEqual(date))
                .filter(invoice -> invoice.getOrderStatus().equals(OrderStatus.RECEIVED))
                .map(Invoice::getPaymentTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long getAccessInDay(LocalDate date) {
        return refreshTokenDAO
                .findAll()
                .stream()
                .filter(refreshToken ->
                        refreshToken.getExpiredDate().toLocalDate().isAfter(date))
                .count();
    }

    @Override
    public Collection<Product> getListProductOutOfStock() {
        return productDAO
                .findAll()
                .stream()
                .filter(product -> product.getQuantityInStock() == 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getTopProductSeller(Long brandId, Long categoryId, Long limit) {
        // Get all items (from all invoices in month)
        List<Invoice> invoicesInMonth = invoiceDAO.findAll()
                .stream()
                .filter(invoice -> invoice.getOrderStatus() == OrderStatus.RECEIVED)
                .filter(invoice
                        -> invoice.getCreatedDate().getMonth().equals(LocalDate.now().getMonth())
                        && invoice.getCreatedDate().getYear() == LocalDate.now().getYear())
                .collect(Collectors.toList());
        Collection<ProductUnit> productUnits = new ArrayList<>();
        invoicesInMonth.forEach(invoice -> {
            productUnits.addAll(productUnitDAO.findProductUnitByInvoiceId(invoice.getId()));
        });

        // Flatten items -> products list (allow duplicate)
        List<Product> products = productUnits.stream()
                .map(unit -> productDAO.findById(unit.getProductId()))
                .filter(product -> brandId == null || Objects.equals(product.getBrandId(), brandId)) // 1-0, 0-0, 0-1
                .filter(product -> categoryId == null || Objects.equals(product.getCategoryId(), categoryId))
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
        List<Map<String, Object>> topProductSeller = new ArrayList<>();
        sortedProducts
                .stream()
                .limit(Optional.ofNullable(limit).orElse(10L)) // default: 10
                .forEach(entry -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("product", entry.getKey());
                    response.put("quantity", entry.getValue());
                    topProductSeller.add(response);
                });
        return topProductSeller;
    }

    @Override
    public List<Map<String, Object>> getTopProductImport(Long brandId, Long categoryId, Long limit) {
        Collection<Product> products = productDAO.findAll();
        Map<Product, Object[]> productImportValue = new HashMap<>();

        // Map product - total import value
        products
                .stream()
                .filter(product -> brandId == null || Objects.equals(product.getBrandId(), brandId)) // 1-0, 0-0, 0-1
                .filter(product -> categoryId == null || Objects.equals(product.getCategoryId(), categoryId))
                .forEach(product -> {
                    Collection<ImportProduct> importProductCollection = importProductDAO.findImportProductByProductId(product.getId());
                    // quantity
                    Long importQuantity = importProductCollection
                            .stream()
                            .map(ImportProduct::getQuantity)
                            .reduce(0L, Long::sum);
                    // value
                    BigDecimal importValue = importProductCollection
                            .stream()
                            .map(importProduct -> importProduct.getImportedPrice().multiply(new BigDecimal(importProduct.getQuantity())))
                            .reduce(BigDecimal.valueOf(0), BigDecimal::add);
                    // add quantity + value
                    productImportValue.put(product, new Object[] { importQuantity, importValue });
                });

        // sort
        List<Map.Entry<Product, Object[]>> sortedProduct = new ArrayList<>(productImportValue.entrySet());
        sortedProduct.sort((entry1, entry2) -> {
            BigDecimal entry1Value = new BigDecimal((char[]) entry1.getValue()[0]);
            BigDecimal entry2Value = new BigDecimal((char[]) entry2.getValue()[0]);
            return entry1Value.compareTo(entry2Value);
        });

        // Put it to list
        List<Map<String, Object>> topProductImport = new ArrayList<>();
        sortedProduct
                .stream()
                .limit(Optional.ofNullable(limit).orElse(10L))
                .forEach(entry -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("product", entry.getKey());
                    data.put("quantity", entry.getValue()[0]);
                    data.put("value", entry.getValue()[1]);
                    topProductImport.add(data);
                });
        return topProductImport;
    }

    @Override
    public int[] getProductRating(String productId) {
        int[] rating = { 0, 0, 0, 0, 0 };
        feedbackDAO
                .findAll()
                .stream()
                .filter(feedback -> feedback.getProductId().equals(productId))
                .forEach(feedback -> {
                    rating[feedback.getRatingPoint() - 1]++;
                });
        return rating;
    }

    @Override
    public List<Map<String, Object>> getTopUserFollower(String productId) {
        Collection<Feedback> feedbackCollection = feedbackDAO.findAll();
        Collection<Comment> commentCollection = commentDAO.findAll();

        List<User> userList = new ArrayList<>();
        feedbackCollection
                .stream()
                .filter(feedback -> productId == null || Objects.equals(feedback.getProductId(), productId))
                .forEach(feedback -> {
                    userList.add(userDAO.findById(feedback.getUserId()));
                });
        commentCollection
                .stream()
                .filter(comment -> productId == null || Objects.equals(comment.getProductId(), productId))
                .forEach(comment -> {
                    User user = userDAO.findUserByPhone(comment.getPhone());
                    if (user != null) {
                        userList.add(user);
                    }
                });

        Map<User, Integer> userFrequencies = new HashMap<>();
        for (User user: userList) {
            userFrequencies.put(user, userFrequencies.getOrDefault(user, 0) + 1);
        }

        List<Map.Entry<User, Integer>> sortedList = new ArrayList<>(userFrequencies.entrySet());
        sortedList.sort(Map.Entry.comparingByValue());

        List<Map<String, Object>> topUserFollower = new ArrayList<>();
        sortedList
                .forEach(entry -> {
                    Map<String, Object> userFollower = new HashMap<>();
                    userFollower.put("user", entry.getKey());
                    userFollower.put("followerPoint", entry.getValue());
                    topUserFollower.add(userFollower);
                });

        return topUserFollower;
    }

    @Override
    public Map<LocalDate, BigDecimal> getPriceInMonth(String productId) {
//        Map<LocalDate, BigDecimal> priceOfProductInMonth = new HashMap<>();
        return null;
    }

    @Override
    public Map<LocalDate, BigDecimal> getIncomeInMonth(String productId) {
        return null;
    }

    @Override
    public Map<LocalDate, BigDecimal> getPayingInMonth(String productId) {
        if (productId != null) {
            return new HashMap<>();
        }
        return null;
    }

    @Override
    public Map<String, BigDecimal> getPriceInYear(String productId) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getIncomeInYear(String productId) {
        if (productId != null) {
            return new HashMap<>();
        }
        return null;
    }

    @Override
    public Map<String, BigDecimal> getPayingInYear(String productId) {
        if (productId != null) {
            return new HashMap<>();
        }
        return null;
    }

    @Override
    public Map<User, BigDecimal> getTopUserValue() {
        return null;
    }

    @Override
    public Map<User, Long> getTopUserAccess() {

        return null;
    }

    @Override
    public Map<LocalDate, Long> getAccessInMonth() {
        Collection<LogSystem> logSystemCollection = logSystemDAO.findAll();

        // Get list 30 days
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListDayToDate(LocalDate.now(), null);
        return null;
    }

    @Override
    public Map<String, Long> getAccessInYear() {
        Collection<LogSystem> logSystemCollection = logSystemDAO.findAll();

        // Get list 12 month
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListMonthToDate(LocalDate.now(), null);
        return null;
    }
}
