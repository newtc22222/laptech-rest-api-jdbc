package com.laptech.restapi.controller.recommend;

import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.DiscountService;
import com.laptech.restapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Api(description = "Recommend system API", tags = "Product Recommend System Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRecommendController {
    private final DiscountService discountService;
    private final ProductService productService;

    @ApiOperation(value = "Get product has discount", response = DataResponse.class)
    @GetMapping("on-sale")
    public ResponseEntity<DataResponse> getProductOnSale(@RequestParam(defaultValue = "false") boolean isCard) {
        Collection<Product> productOnSaleList = productService.findAll()
                .stream()
                // get product has discount on date
                .filter(product -> discountService
                        .getDiscountsOfProduct(product.getId())
                        .stream()
                        .anyMatch(discount -> discount.getAppliedDate().isBefore(LocalDateTime.now())
                                && discount.getEndedDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
        if(isCard) {
            return DataResponse.getCollectionSuccess("Get product on sales", productService.getProductCardDTO(productOnSaleList));
        }
        return DataResponse.getCollectionSuccess("Get product on sales", productOnSaleList);
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
