package com.laptech.restapi.controller.recommend;

import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.DiscountService;
import com.laptech.restapi.service.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "Recommend system API", value = "Product Recommend System Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ProductRecommendController {
    private final DiscountService discountService;
    private final ProductService productService;

    @GetMapping("on-sale")
    public ResponseEntity<Collection<Product>> getProductOnSale() {
        Collection<Product> productOnSaleList = productService.findAll()
                .stream()
                // get product has discount on date
                .filter(product -> discountService
                        .getDiscountsOfProduct(product.getId())
                        .stream()
                        .anyMatch(discount -> discount.getAppliedDate().isBefore(LocalDateTime.now())
                                && discount.getEndedDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productOnSaleList);
    }

    /**
     * Use Slope one algorithm: Calculate frequencies, actions and rating for product / user
     * -> recommend product to user
     * @see <a href="https://www.baeldung.com/java-collaborative-filtering-recommendations">Baeldung.com | Collaborative Filtering</a>
     */
    @GetMapping("recommend")
    public ResponseEntity<Product> getProductRecommend() {
        // models
        Map<User, HashMap<Product, Double>> data;

        // differences and frequencies matrices

        // predictions

        return null;
    }
}