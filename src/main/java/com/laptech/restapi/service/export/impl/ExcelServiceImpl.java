package com.laptech.restapi.service.export.impl;

import com.laptech.restapi.common.constant.ExcelHeader;
import com.laptech.restapi.dao.*;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.Role;
import com.laptech.restapi.service.export.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExcelServiceImpl implements ExcelService {
    private final BannerDAO bannerDAO;
    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final CommentDAO commentDAO;
    private final DiscountDAO discountDAO;
    private final FeedbackDAO feedbackDAO;
    private final ImportProductDAO importProductDAO;
    private final InvoiceDAO invoiceDAO;
    private final LabelDAO labelDAO;
    private final ProductDAO productDAO;
    private final RoleDAO roleDAO;
    private final UserDAO userDAO;

    private Map<String, Collection<Object[]>> getAllRecords(Collection<String> options) {
        Map<String, Collection<Object[]>> records = new HashMap<>();
        // common
        Collection<Object[]> record = new ArrayList<>();
        // handle
        if(options.contains("all") || options.contains("banner")) {
            record.add(ExcelHeader.bannerHeader);
            bannerDAO.findAll().forEach(banner -> {
                Object[] row = {
                        banner.getId(),
                        banner.getPath(),
                        banner.getType(),
                        banner.getLinkProduct(),
                        banner.getUsedDate(),
                        banner.getEndedDate(),
                        banner.getCreatedDate(),
                        banner.getModifiedDate()
                };
                record.add(row);
            });
            records.put("banner", record);
        }
        if(options.contains("all") || options.contains("brand")) {
            record.clear();
            record.add(ExcelHeader.brandHeader);
            brandDAO.findAll().forEach(brand -> {
                Object[] row = {
                        brand.getId(),
                        brand.getName(),
                        brand.getCountry(),
                        brand.getEstablishDate(),
                        brand.getLogo(),
                        brand.getCreatedDate(),
                        brand.getModifiedDate(),
                        productDAO
                                .findProductByBrandId(brand.getId())
                                .stream().map(Product::getName)
                                .collect(Collectors.joining(", "))
                };
                record.add(row);
            });
            records.put("brand", record);
        }
        if(options.contains("all") || options.contains("category")) {
            record.clear();
            record.add(ExcelHeader.categoryHeader);
            categoryDAO.findAll().forEach(category -> {
                Object[] row = {
                        category.getId(),
                        category.getName(),
                        category.getImage(),
                        category.getDescription(),
                        category.getCreatedDate(),
                        category.getModifiedDate()
                };
                record.add(row);
            });
            records.put("category", record);
        }
        if(options.contains("all") || options.contains("comment")) {
            record.clear();
            record.add(ExcelHeader.commentHeader);
            commentDAO.findAll().forEach(comment -> {
                Object[] row = {
                        comment.getId(),
                        comment.getRootCommentId(),
                        comment.getProductId(),
                        comment.getUsername(),
                        comment.getPhone(),
                        comment.getContent(),
                        comment.getCreatedDate(),
                        comment.getModifiedDate()
                };
                record.add(row);
            });
            records.put("comment", record);
        }
        if(options.contains("all") || options.contains("discount")) {
            record.clear();
            record.add(ExcelHeader.discountHeader);
            discountDAO.findAll().forEach(discount -> {
                Object[] row = {
                        discount.getId(),
                        discount.getCode(),
                        discount.getRate(),
                        discount.getAppliedType().toString(),
                        discount.getMaxAmount(),
                        discount.getAppliedDate(),
                        discount.getEndedDate(),
                        discount.getCreatedDate(),
                        discount.getModifiedDate()
                };
                record.add(row);
            });
            records.put("discount", record);
        }
        if(options.contains("all") || options.contains("feedback")) {
            record.clear();
            record.add(ExcelHeader.feedbackHeader);
            feedbackDAO.findAll().forEach(feedback -> {
                Object[] row = {
                        feedback.getId(),
                        feedback.getProductId(),
                        feedback.getUserId(),
                        Optional.ofNullable(userDAO.findById(feedback.getUserId()).getName()).orElse("Anonymous"),
                        feedback.getContent(),
                        feedback.getRatingPoint(),
                        feedback.getCreatedDate(),
                        feedback.getModifiedDate()
                };
                record.add(row);
            });
            records.put("feedback", record);
        }
        if(options.contains("all") || options.contains("importProduct")) {
            record.clear();
            record.add(ExcelHeader.importProductHeader);
            importProductDAO.findAll().forEach(importProduct -> {
                Object[] row = {
                        importProduct.getId(),
                        importProduct.getProductId(),
                        importProduct.getQuantity(),
                        importProduct.getImportedPrice(),
                        importProduct.getImportedDate(),
                        importProduct.getCreatedDate(),
                        importProduct.getModifiedDate()
                };
                record.add(row);
            });
            records.put("import product", record);
        }
        if(options.contains("all") || options.contains("invoice")) {
            record.clear();
            record.add(ExcelHeader.invoiceHeader);
            invoiceDAO.findAll().forEach(invoice -> {
                Object[] row = {
                        invoice.getId(),
                        invoice.getUserId(),
                        Optional.ofNullable(userDAO.findById(invoice.getUserId()).getName()).orElse("Anonymous"),
                        invoice.getAddress(),
                        invoice.getPhone(),
                        invoice.getPaymentAmount(),
                        invoice.getShipCost(),
                        invoice.getDiscountAmount(),
                        invoice.getTax(),
                        invoice.getPaymentTotal(),
                        invoice.getPaymentType(),
                        invoice.isPaid() ? "Đã thanh toán" : "Chờ thanh toán",
                        invoice.getOrderStatus().toString(),
                        invoice.getNote(),
                        invoice.getCreatedDate(),
                        invoice.getModifiedDate()
                };
                record.add(row);
            });
            records.put("invoice", record);
        }
        if(options.contains("all") || options.contains("label")) {
            record.clear();
            record.add(ExcelHeader.labelHeader);
            labelDAO.findAll().forEach(label -> {
                Object[] row = {
                        label.getId(),
                        label.getName(),
                        label.getTitle(),
                        label.getDescription(),
                        label.getCreatedDate(),
                        label.getModifiedDate()
                };
                record.add(row);
            });
            records.put("label", record);
        }
        if(options.contains("all") || options.contains("product")) {
            record.clear();
            record.add(ExcelHeader.productHeader);
            productDAO.findAll().forEach(product -> {
                Object[] row = {
                        product.getId(),
                        Optional.ofNullable(brandDAO.findById(product.getBrandId()).getName()).orElse("Unknown"),
                        Optional.ofNullable(categoryDAO.findById(product.getCategoryId()).getName()).orElse("Unknown"),
                        product.getName(),
                        product.getReleasedDate(),
                        product.getQuantityInStock(),
                        product.getListedPrice().toString(),
                        product.getCreatedDate(),
                        product.getModifiedDate()
                };
                record.add(row);
            });
            records.put("product", record);
        }
        if(options.contains("all") || options.contains("role")) {
            record.clear();
            record.add(ExcelHeader.roleHeader);
            roleDAO.findAll().forEach(role -> {
                Object[] row = {
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getCreatedDate(),
                        role.getModifiedDate()
                };
                record.add(row);
            });
            records.put("role", record);
        }
        if(options.contains("all") || options.contains("user")) {
            record.clear();
            record.add(ExcelHeader.userHeader);
            userDAO.findAll().forEach(user -> {
                Object[] row = {
                        user.getId(),
                        user.getName(),
                        user.getGender().toString(),
                        user.getDateOfBirth(),
                        user.getPhone(),
                        user.getEmail(),
                        user.isActive() ? "Đang hoạt động" : "Đã khóa",
                        user.getCreatedDate(),
                        user.getModifiedDate(),
                        roleDAO
                                .findRoleByUserId(user.getId())
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.joining(", "))
                };
                record.add(row);
            });
            records.put("user", record);
        }
        return records;
    }

    @Override
    public void writeDataToExcel(HttpServletResponse response, Collection<String> options) {
        response.setContentType("application/octet-stream");
        Map<String, Collection<Object[]>> records = getAllRecords(options);

        XSSFWorkbook workbook = new XSSFWorkbook();
        records.forEach((sheetKey, sheetRecord) -> {
            AtomicInteger rowCount = new AtomicInteger(0);
            Sheet sheet = workbook.createSheet(sheetKey);

            sheetRecord.forEach(row -> {
                Row newRow = sheet.createRow(rowCount.getAndIncrement());
                for (int i = 0; i < row.length; i++) {
                    Cell cell = newRow.createCell(i);
                    cell.setCellValue((String) row[i]);
                }
            });
        });

        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
