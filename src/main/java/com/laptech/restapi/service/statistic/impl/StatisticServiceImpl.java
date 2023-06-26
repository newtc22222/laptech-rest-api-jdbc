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
import java.time.format.DateTimeFormatter;
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

    private Collection<ProductUnit> getFlattenProductUnitCollection(Collection<Invoice> invoices) {
        Collection<ProductUnit> productUnits = new ArrayList<>();
        invoices
                .stream()
                .filter(invoice -> invoice.getOrderStatus().equals(OrderStatus.RECEIVED))
                .forEach(invoice -> productUnits.addAll(productUnitDAO.findProductUnitByInvoiceId(invoice.getId())));
        return productUnits;
    }

    private BigDecimal calculateIncomeTotalValue(Collection<Invoice> invoices, String productId) {
        if(invoices != null) {
            Collection<ProductUnit> productUnits = getFlattenProductUnitCollection(invoices);
            // Calculate
            return productUnits
                    .stream()
                    .filter(productUnit -> productUnit.getProductId().equals(productId))
                    .map(productUnit -> productUnit.getDiscountPrice().multiply(BigDecimal.valueOf(productUnit.getQuantity())))
                    .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        }
        return BigDecimal.valueOf(0);
    }

    private BigDecimal calculatePayingTotalValue(Collection<ImportProduct> importProducts, String productId) {
        if(importProducts != null) {
            return importProducts
                    .stream()
                    .filter(importProduct -> importProduct.getProductId().equals(productId))
                    .map(importProduct -> importProduct.getImportedPrice().multiply(BigDecimal.valueOf(importProduct.getQuantity())))
                    .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        }
        return BigDecimal.valueOf(0);
    }

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
        invoicesInMonth.forEach(invoice -> productUnits.addAll(productUnitDAO.findProductUnitByInvoiceId(invoice.getId())));

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
                .forEach(feedback -> rating[feedback.getRatingPoint() - 1]++);
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
                .forEach(feedback -> userList.add(userDAO.findById(feedback.getUserId())));
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
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListDayToDate(LocalDate.now(), 30);

        Map<LocalDate, BigDecimal> incomeInMonth = new HashMap<>();
        dateCollection.forEach(localDate -> {
            // get all item was sold
            Collection<Invoice> invoices = invoiceDAO.findInvoiceByDate(localDate);
            incomeInMonth.put(localDate, calculateIncomeTotalValue(invoices, productId));
        });
        return incomeInMonth;
    }

    @Override
    public Map<LocalDate, BigDecimal> getPayingInMonth(String productId) {
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListDayToDate(LocalDate.now(), 30);

        // get all items
        Map<LocalDate, BigDecimal> payingInMonth = new HashMap<>();
        dateCollection.forEach(localDate -> {
            Collection<ImportProduct> importProducts = importProductDAO.findImportProductByDate(localDate);
            payingInMonth.put(localDate, calculatePayingTotalValue(importProducts, productId));
        });
        return payingInMonth;
    }

    @Override
    public Map<String, BigDecimal> getPriceInYear(String productId) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getIncomeInYear(String productId) {
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListMonthToDate(LocalDate.now(), 12);

        Map<String, BigDecimal> incomeInYear = new HashMap<>();
        dateCollection.forEach(localDate -> {
            String key = localDate.getMonth() + "-" + localDate.getYear();
            Collection<Invoice> invoices = invoiceDAO.findInvoiceByDateRange(
                    LocalDate.of(localDate.getYear(), localDate.getMonth(), 1).atStartOfDay(),
                    LocalDate.of(localDate.getYear(), localDate.getMonth().plus(1), 1).atStartOfDay()
            );
            incomeInYear.put(key, calculateIncomeTotalValue(invoices, productId));
        });
        return incomeInYear;
    }

    @Override
    public Map<String, BigDecimal> getPayingInYear(String productId) {
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListMonthToDate(LocalDate.now(), 12);

        Map<String, BigDecimal> payingInYear = new HashMap<>();
        dateCollection.forEach(localDate -> {
            String key = localDate.getMonth() + "-" + localDate.getYear();
            Collection<ImportProduct> importProducts = importProductDAO.findImportProductByDateRange(
                    LocalDate.of(localDate.getYear(), localDate.getMonth(), 1).atStartOfDay(),
                    LocalDate.of(localDate.getYear(), localDate.getMonth().plus(1), 1).atStartOfDay()
            );
            payingInYear.put(key, calculatePayingTotalValue(importProducts, productId));
        });
        return payingInYear;
    }

    @Override
    public List<Map<String, Object>> getTopUserValue(LocalDate dateFilter) {
        Collection<Invoice> invoices = invoiceDAO.findAll()
                .stream()
                .filter(invoice -> invoice.getOrderStatus().equals(OrderStatus.RECEIVED))
                .filter(invoice -> dateFilter == null
                        || (invoice.getModifiedDate().getMonth().equals(dateFilter.getMonth()) && invoice.getModifiedDate().getYear() == dateFilter.getYear()))
                .collect(Collectors.toList());

        Map<User, BigDecimal> userPaying = new HashMap<>();
        invoices.forEach(invoice -> {
            User user = userDAO.findById(invoice.getUserId());
            userPaying.put(user, userPaying.getOrDefault(user, BigDecimal.valueOf(0)).add(invoice.getPaymentTotal()));
        });

        List<Map.Entry<User, BigDecimal>> sortedUserPaying = new ArrayList<>(userPaying.entrySet());
        sortedUserPaying.sort(Map.Entry.comparingByValue());
        List<Map<String,Object >> topUserPaying = new ArrayList<>();
        sortedUserPaying
                .forEach(userBigDecimalEntry -> {
                    Map<String, Object> userBigDecimalMap = new HashMap<>();
                    userBigDecimalMap.put("user", userBigDecimalEntry.getKey());
                    userBigDecimalMap.put("value", userBigDecimalEntry.getValue());
                    topUserPaying.add(userBigDecimalMap);
                });
        return topUserPaying;
    }

    @Override
    public List<Map<String, Object>> getTopUserAccess(LocalDate dateFilter) {
        Collection<RefreshToken> refreshTokens = refreshTokenDAO.findAll();
        Map<User, Long> userAccess = new HashMap<>();
        refreshTokens
                .stream()
                .filter(refreshToken -> dateFilter == null || refreshToken.getCreatedDate().toLocalDate().equals(dateFilter))
                .forEach(refreshToken -> {
                    User user = userDAO.findById(refreshToken.getUserId());
                    userAccess.put(user, userAccess.getOrDefault(user, 0L) + 1);
                });
        List<Map.Entry<User, Long>> sortedUserAccess = new ArrayList<>(userAccess.entrySet());
        sortedUserAccess.sort(Map.Entry.comparingByValue());

        List<Map<String, Object>> topUserAccess = new ArrayList<>();
        sortedUserAccess.forEach(userLongEntry -> {
            Map<String, Object> topUser = new HashMap<>();
            topUser.put("user", userLongEntry.getKey());
            topUser.put("role", roleDAO.findRoleByUserId(userLongEntry.getKey().getId()));
            topUser.put("accessTime", userLongEntry.getValue());
            topUserAccess.add(topUser);
        });

        return topUserAccess;
    }

    @Override
    public Map<LocalDate, Long> getAccessInMonth() {
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListDayToDate(LocalDate.now(), null);
        Collection<RefreshToken> refreshTokens = refreshTokenDAO.findAll();

        Map<LocalDate, Long> accessInMonth = new HashMap<>();
        dateCollection.forEach(localDate -> accessInMonth.put(
                localDate,
                refreshTokens
                        .stream()
                        .filter(refreshToken -> refreshToken.getCreatedDate().toLocalDate().equals(localDate))
                        .count()
        ));
        return accessInMonth;
    }

    @Override
    public Map<String, Long> getAccessInYear() {
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListMonthToDate(LocalDate.now(), null);
        Collection<RefreshToken> refreshTokens = refreshTokenDAO.findAll();

        Map<String, Long> accessInYear = new HashMap<>();
        dateCollection.forEach(localDate -> {
            String key = localDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            LocalDate startDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
            LocalDate endDate = LocalDate.of(localDate.getYear(), localDate.getMonth().plus(1), 1);
            accessInYear.put(
                    key,
                    refreshTokens
                            .stream()
                            .filter(refreshToken -> refreshToken.getCreatedDate().isAfter(startDate.atStartOfDay()))
                            .filter(refreshToken -> refreshToken.getCreatedDate().isBefore(endDate.atStartOfDay()))
                            .count()
            );
        });
        return accessInYear;
    }

    @Override
    public Map<LocalDate, Long> getInteractInMonth() {
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListDayToDate(LocalDate.now(), 30);
        Collection<LogSystem> logSystemCollection = logSystemDAO.findAll();
        Map<LocalDate, Long> interactInMonth = new HashMap<>();
        dateCollection.forEach(localDate -> interactInMonth.put(
                localDate,
                logSystemCollection
                        .stream()
                        .filter(logSystem -> logSystem.getActionTime().toLocalDate().equals(localDate))
                        .count()
        ));
        return interactInMonth;
    }

    @Override
    public Map<String, Long> getInteractInYear() {
        Collection<LocalDate> dateCollection = BuildDateTimeCollection.getListMonthToDate(LocalDate.now(), 12);
        Collection<LogSystem> logSystemCollection = logSystemDAO.findAll();
        Map<String, Long> interactInYear = new HashMap<>();
        dateCollection.forEach(localDate -> {
            String key = localDate.getMonth() + "-" + localDate.getYear();
            LocalDate startDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
            LocalDate endDate = LocalDate.of(localDate.getYear(), localDate.getMonth().plus(1), 1);
            interactInYear.put(
                    key,
                    logSystemCollection
                            .stream()
                            .filter(logSystem -> logSystem.getActionTime().isAfter(startDate.atStartOfDay()))
                            .filter(logSystem -> logSystem.getActionTime().isBefore(endDate.atStartOfDay()))
                            .count()
            );
        });
        return interactInYear;
    }
}
