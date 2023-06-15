package com.laptech.restapi.controller.export;

import com.laptech.restapi.service.*;
import com.laptech.restapi.service.export.ExcelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "Export Excel API", value = "Export Excel Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/export-excel")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ExcelFileController {
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
    private final ProductImageService productImageService;
    private final ProductUnitService productUnitService;
    private final RoleService roleService;
    private final UserService userService;

    private final ExcelService excelService;

    public void exportExcel(@RequestParam String[] options,
                            HttpServletResponse response) {

    }
}
