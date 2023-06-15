package com.laptech.restapi.controller.recommend;

import com.laptech.restapi.model.Product;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Recommend system API", value = "Product Recommend System Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ProductRecommendController {
    @GetMapping("on-sale")
    public ResponseEntity<Product> getProductOnSale() {
        return null;
    }

    @GetMapping("recommend")
    public ResponseEntity<Product> getProductRecommend() {
        return null;
    }
}
