package com.laptech.restapi.service;

import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.dto.response.ProductCardDTO;
import com.laptech.restapi.dto.response.ProductDetailDTO;
import com.laptech.restapi.model.Product;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface ProductService extends BaseService<Product, String> {
    void updatePrice(ProductPriceDTO productPriceDTO);

    // accessory
    void insertAccessory(String productId, String accessoryId);

    void updateMultipleAccessories(String productId, List<String> accessoryIdAddList, List<String> accessoryRemoveList);

    void removeAccessory(String productId, String accessoryId);

    // discount
    void insertDiscount(String productId, long discountId);

    void updateMultipleDiscounts(String productId, List<Long> discountIdAddList, List<Long> discountIdRemoveList);

    void removeDiscount(String productId, long discountId);

    // label
    void insertLabel(String productId, long labelId);

    void updateMultipleLabels(String productId, List<Long> labelIdAddList, List<Long> labelIdRemoveList);

    void removeLabel(String productId, long labelId);

    Collection<ProductCardDTO> getProductCardDTO(Collection<Product> productCollection);

    ProductDetailDTO getProductDetail(Product product);

    Set<Product> findAccessoryOfProduct(String productId);

    Collection<Product> findProductByBrandId(long brandId);

    Collection<Product> findProductByCategoryId(long categoryId);
}
