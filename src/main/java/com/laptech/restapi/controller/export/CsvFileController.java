package com.laptech.restapi.controller.export;

import com.laptech.restapi.common.constant.CsvHeader;
import com.laptech.restapi.model.*;
import com.laptech.restapi.service.*;
import com.laptech.restapi.service.export.CsvService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(description = "Export CSV API", tags = "Export CSV Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/export-csv")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class CsvFileController {
    private final BannerService bannerService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final DiscountService discountService;
    private final FeedbackService feedbackService;
    private final ImportProductService importProductService;
    private final InvoiceService invoiceService;
    private final LabelService labelService;
    private final ProductService productService;
    private final RoleService roleService;
    private final UserService userService;

    private final CsvService csvService;

    @GetMapping("banners")
    public void exportBanners(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Banner> banners = bannerService.findAll();
        banners.forEach(banner -> {
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
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.bannerHeader, records);
    }

    @GetMapping("brands")
    public void exportBrands(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Brand> brands = brandService.findAll();
        brands.forEach(brand -> {
            Object[] row = {
                    brand.getId(),
                    brand.getName(),
                    brand.getCountry(),
                    brand.getEstablishDate(),
                    brand.getLogo(),
                    brand.getCreatedDate(),
                    brand.getModifiedDate(),
                    productService
                            .findProductByBrandId(brand.getId())
                            .stream().map(Product::getName)
                            .collect(Collectors.joining(", "))
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.brandHeader, records);
    }

    @GetMapping("categories")
    public void exportCategory(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Category> categories = categoryService.findAll();
        categories.forEach(category -> {
            Object[] row = {
                    category.getId(),
                    category.getName(),
                    category.getImage(),
                    category.getDescription(),
                    category.getCreatedDate(),
                    category.getModifiedDate()
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.categoryHeader, records);
    }

    @GetMapping("comments")
    public void exportComments(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Comment> comments = commentService.findAll();
        comments.forEach(comment -> {
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
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.commentHeader, records);
    }

    @GetMapping("discounts")
    public void exportDiscounts(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Discount> discounts = discountService.findAll();
        discounts.forEach(discount -> {
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
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.discountHeader, records);
    }

    @GetMapping("feedbacks")
    public void exportFeedbacks(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Feedback> feedbacks = feedbackService.findAll();
        feedbacks.forEach(feedback -> {
            Object[] row = {
                    feedback.getId(),
                    feedback.getProductId(),
                    feedback.getUserId(),
                    Optional.ofNullable(userService.findById(feedback.getUserId()).getName()).orElse("Anonymous"),
                    feedback.getContent(),
                    feedback.getRatingPoint(),
                    feedback.getCreatedDate(),
                    feedback.getModifiedDate()
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.feedbackHeader, records);
    }

    @GetMapping("import-products")
    public void exportImportProducts(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<ImportProduct> importProducts = importProductService.findAll();
        importProducts.forEach(importProduct -> {
            Object[] row = {
                    importProduct.getId(),
                    importProduct.getProductId(),
                    importProduct.getQuantity(),
                    importProduct.getImportedPrice(),
                    importProduct.getImportedDate(),
                    importProduct.getCreatedDate(),
                    importProduct.getModifiedDate()
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.importProductHeader, records);
    }

    @GetMapping("invoices")
    public void exportInvoices(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Invoice> invoices = invoiceService.findAll();
        invoices.forEach(invoice -> {
            Object[] row = {
                    invoice.getId(),
                    invoice.getUserId(),
                    Optional.ofNullable(userService.findById(invoice.getUserId()).getName()).orElse("Anonymous"),
                    invoice.getAddress(),
                    invoice.getPhone(),
                    invoice.getPaymentAmount(),
                    invoice.getShipCost(),
                    invoice.getDiscountAmount(),
                    invoice.getTax(),
                    invoice.getPaymentTotal(),
                    invoice.getPaymentType(),
                    invoice.isPaid() ? "Was paid" : "Waiting ...",
                    invoice.getOrderStatus().toString(),
                    invoice.getNote(),
                    invoice.getCreatedDate(),
                    invoice.getModifiedDate()
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.invoiceHeader, records);
    }

    @GetMapping("labels")
    public void exportLabels(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Label> labels = labelService.findAll();
        labels.forEach(label -> {
            Object[] row = {
                    label.getId(),
                    label.getName(),
                    label.getTitle(),
                    label.getDescription(),
                    label.getCreatedDate(),
                    label.getModifiedDate()
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.labelHeader, records);
    }

    @GetMapping("products")
    public void exportProducts(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Product> products = productService.findAll();
        products.forEach(product -> {
            Object[] row = {
                    product.getId(),
                    Optional.ofNullable(brandService.findById(product.getBrandId()).getName()).orElse("Unknown"),
                    Optional.ofNullable(categoryService.findById(product.getCategoryId()).getName()).orElse("Unknown"),
                    product.getName(),
                    product.getReleasedDate(),
                    product.getQuantityInStock(),
                    product.getListedPrice().toString(),
                    product.getCreatedDate(),
                    product.getModifiedDate()
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.productHeader, records);
    }

    @GetMapping("roles")
    public void exportRoles(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<Role> roles = roleService.findAll();
        roles.forEach(role -> {
            Object[] row = {
                    role.getId(),
                    role.getName(),
                    role.getDescription(),
                    role.getCreatedDate(),
                    role.getModifiedDate()
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.roleHeader, records);
    }

    @GetMapping("user")
    public void exportUsers(HttpServletResponse response) {
        List<Object[]> records = new ArrayList<>();
        Collection<User> users = userService.findAll();
        users.forEach(user -> {
            Object[] row = {
                    user.getId(),
                    user.getName(),
                    user.getGender().toString(),
                    user.getDateOfBirth(),
                    user.getPhone(),
                    user.getEmail(),
                    user.isActive() ? "Active" : "Inactive",
                    user.getCreatedDate(),
                    user.getModifiedDate(),
                    roleService
                            .findRoleByUserId(user.getId())
                            .stream()
                            .map(Role::getName)
                            .collect(Collectors.joining(", "))
            };
            records.add(row);
        });
        csvService.writeDataToCsv(response, CsvHeader.userHeader, records);
    }
}
