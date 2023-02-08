package com.laptech.restapi.service;

import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.dto.response.ProductCardDTO;
import com.laptech.restapi.dto.response.ProductDetailDTO;
import com.laptech.restapi.model.Product;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface ProductService extends BaseService<Product, String> {
    void updatePrice(ProductPriceDTO productPriceDTO);

    // accessory
    void insertAccessory(String productId, String accessoryId);

    void removeAccessory(String productId, String accessoryId);

    // discount
    void insertDiscount(String productId, long discountId);

    void removeDiscount(String productId, long discountId);

    // label
    void insertLabel(String productId, long labelId);

    void removeLabel(String productId, long labelId);

    Collection<ProductCardDTO> getProductCardDTO(Collection<Product> productCollection);

    ProductDetailDTO getProductDetail(Product product);

    Set<Product> findAccessoryOfProduct(String productId);

    List<Product> findProductByBrandId(long brandId);

    List<Product> findProductByCategoryId(long categoryId);

    /**
     * Filter options:
     * <ul>
     * <li>name</li>
     * <li>brand id</li>
     * <li>category id</li>
     * <li>released year</li>
     * <li>start price + end price</li>
     * <li><b>label name</b></li>
     * </ul>
     * <b>In future plan ...</b>
     * <ul>
     * <li>on discount</li>
     * <li>top seller</li>
     * </ul>
     * <br/>
     * <b>Below is laptop's properties <i>[remove]</i></b>
     * ram capacity | cpu brand + cpu type | screen size | graphic card type | hard drive type + capacity
     * <br/>
     */
    Set<Product> filter(Map<String, Object> params);
}
