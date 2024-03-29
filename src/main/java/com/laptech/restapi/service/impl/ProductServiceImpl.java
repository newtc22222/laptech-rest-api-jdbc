package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.*;
import com.laptech.restapi.dto.filter.ProductFilter;
import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.dto.response.ProductCardDTO;
import com.laptech.restapi.dto.response.ProductDetailDTO;
import com.laptech.restapi.model.Discount;
import com.laptech.restapi.model.Label;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.ProductImage;
import com.laptech.restapi.service.ProductService;
import com.laptech.restapi.util.ConvertMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;

    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final DiscountDAO discountDAO;
    private final LabelDAO labelDAO;
    private final ProductImageDAO productImageDAO;

    private final ProductAccessoryDAO productAccessoryDAO;
    private final ProductDiscountDAO productDiscountDAO;
    private final ProductLabelDAO productLabelDAO;

    @Override
    public Product insert(Product product) {
        if (productDAO.isExists(product)) {
            throw new ResourceAlreadyExistsException("[Info] This product has already existed in system!");
        }
        String newProductId = productDAO.insert(product);
        if (newProductId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new product!");
        }
        return productDAO.findById(newProductId);
    }

    @Override
    public void update(Product product, String productId) {
        // check

        Product oldProduct = productDAO.findById(productId);
        if (oldProduct == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        } else {
            oldProduct.setBrandId(product.getBrandId());
            oldProduct.setCategoryId(product.getCategoryId());
            oldProduct.setName(product.getName());
            oldProduct.setReleasedDate(product.getReleasedDate());
            oldProduct.setQuantityInStock(product.getQuantityInStock());
            oldProduct.setListedPrice(product.getListedPrice());
            oldProduct.setSpecifications(product.getSpecifications());
            oldProduct.setDescriptionDetail(product.getDescriptionDetail());
            oldProduct.setUpdateBy(product.getUpdateBy());

            if (productDAO.update(oldProduct) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this product!");
            }
        }
    }

    @Override
    public void delete(String productId, String updateBy) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        } else {
            if (productDAO.delete(productId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this product!");
            }
        }
    }

    @Override
    public long count() {
        return productDAO.count();
    }

    @Override
    public Collection<Product> findAll(String sortBy, String sortDir, Long page, Long size) {
        return productDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Product> findWithFilter(Map<String, Object> params) {
        Set<Product> productSet = new HashSet<>(productDAO.findAll());
        
        if (params.get("brandId") != null) {
            String[] brandIdList = (String[]) params.get("brandId");
            Set<Product> products = new HashSet<>();
            Arrays.asList(brandIdList).forEach(brandId ->
                    products.addAll(productDAO.findProductByBrandId(Long.parseLong(brandId)))
            );
            productSet.removeIf(item -> !products.contains(item));
            params.remove("brandId");
        }
        if (params.get("categoryId") != null) {
            String[] categoryIdList = (String[]) params.get("categoryId");
            Set<Product> products = new HashSet<>();
            Arrays.asList(categoryIdList).forEach(categoryId ->
                    products.addAll(productDAO.findProductByCategoryId(Long.parseLong(categoryId)))
            );
            productSet.removeIf(item -> !products.contains(item));
            params.remove("categoryId");
        }
        if (params.get("labelId") != null) {
            String[] labelIdList = (String[]) params.get("label");
            Set<Product> products = new HashSet<>();
            Arrays.asList(labelIdList).forEach(labelId ->
                    products.addAll(productDAO.findProductByLabelId(Long.parseLong(labelId)))
            );
            productSet.removeIf(item -> !products.contains(item));
            params.remove("labelId");
        }
        if (params.get("startPrice") != null && params.get("endPrice") != null) {
            Collection<Product> productList = productDAO.findProductByPriceRange(
                    new BigDecimal(String.valueOf(params.get("startPrice"))),
                    new BigDecimal(String.valueOf(params.get("endPrice")))
            );
            productSet.removeIf(item -> !productList.contains(item));
        }

        ProductFilter filter = new ProductFilter(ConvertMap.changeAllValueFromObjectToString(params));
        Collection<Product> products = productDAO.findWithFilter(filter);
        productSet.removeIf(item -> !products.contains(item));
        return productSet;
    }

    @Override
    public Product findById(String productId) {
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return product;
    }

    @Override
    public void updatePrice(ProductPriceDTO productPriceDTO) {
        // check

        if (productDAO.findById(productPriceDTO.getId()) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productPriceDTO.getId());
        } else {
            if (productDAO.updatePrice(productPriceDTO, productPriceDTO.getUpdateBy()) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this product!");
            }
        }
    }

    @Override
    public void insertAccessory(String productId, String accessoryId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        if (productDAO.findById(accessoryId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + accessoryId);
        }
        if (productAccessoryDAO.insert(productId, accessoryId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to insert accessory into product!");
        }
    }

    @Override
    public void updateMultipleAccessories(String productId, List<String> accessoryIdAddList, List<String> accessoryRemoveList) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        for (List<String> strings : Arrays.asList(accessoryIdAddList, accessoryRemoveList)) {
            strings.forEach(accessoryId -> {
                if (productDAO.findById(accessoryId) == null)
                    throw new ResourceNotFoundException("[Info] Cannot find accessory with id=" + productId);
            });
        }
        if (productAccessoryDAO.updateMultiple(productId, accessoryIdAddList, accessoryRemoveList) == 0) {
            throw new InternalServerErrorException("[Error] Failed to update accessories of product!");
        }
    }

    @Override
    public void removeAccessory(String productId, String accessoryId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        if (productDAO.findById(accessoryId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + accessoryId);
        }
        if (productAccessoryDAO.remove(productId, accessoryId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to remove accessory into product!");
        }
    }

    @Override
    public void insertDiscount(String productId, long discountId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        if (discountDAO.findById(discountId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find discount with id=" + discountId);
        }
        if (productDiscountDAO.insert(productId, discountId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to insert discount into product!");
        }
    }

    @Override
    public void updateMultipleDiscounts(String productId, List<Long> discountIdAddList, List<Long> discountIdRemoveList) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        for (List<Long> discountIds : Arrays.asList(discountIdAddList, discountIdRemoveList)) {
            discountIds.forEach(discountId -> {
                if (discountDAO.findById(discountId) == null)
                    throw new ResourceNotFoundException("[Info] Cannot find discount with id=" + discountId);
            });
        }
        if (productDiscountDAO.updateMultiple(productId, discountIdAddList, discountIdRemoveList) == 0) {
            throw new InternalServerErrorException("[Error] Failed to update discounts of product!");
        }
    }

    @Override
    public void removeDiscount(String productId, long discountId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        if (discountDAO.findById(discountId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find discount with id=" + discountId);
        }
        if (productDiscountDAO.remove(productId, discountId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to remove discount from product!");
        }
    }

    @Override
    public void insertLabel(String productId, long labelId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        if (labelDAO.findById(labelId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
        }
        if (productLabelDAO.insert(productId, labelId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to insert label into product!");
        }
    }

    @Override
    public void updateMultipleLabels(String productId, List<Long> labelIdAddList, List<Long> labelIdRemoveList) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        for (List<Long> labelIds : Arrays.asList(labelIdAddList, labelIdRemoveList)) {
            labelIds.forEach(labelId -> {
                if (labelDAO.findById(labelId) == null)
                    throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
            });
        }
        if (productLabelDAO.updateMultiple(productId, labelIdAddList, labelIdRemoveList) == 0) {
            throw new InternalServerErrorException("[Error] Failed to update labels of product!");
        }
    }

    @Override
    public void removeLabel(String productId, long labelId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        if (labelDAO.findById(labelId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
        }
        if (productLabelDAO.remove(productId, labelId) == 0) {
            throw new InternalServerErrorException("[Error] Failed to remove label from product!");
        }
    }

    private BigDecimal getDiscountPrice(Discount discount, Product product) {
        if (discount != null) {
            BigDecimal discountAmount = product.getListedPrice().multiply(BigDecimal.valueOf(discount.getRate())); // discountAmount = price * rate
            if (discountAmount.compareTo(discount.getMaxAmount()) >= 0) { // greater than or equal
                return product.getListedPrice().subtract(discount.getMaxAmount()); // price - maxAmount
            }
            return product.getListedPrice().subtract(discountAmount); // price - discountAmount
        }
        return product.getListedPrice();
    }

    @Override
    public Collection<ProductCardDTO> getProductCardDTO(Collection<Product> productCollection) {
        return productCollection
                .stream()
                .map(product -> {
                    ProductCardDTO cardDTO = new ProductCardDTO();
                    cardDTO.setId(product.getId());
                    cardDTO.setName(product.getName());

                    // Get images
                    Collection<ProductImage> imageList = productImageDAO.findProductImageByProductId(product.getId());
                    cardDTO.setImagesRepresentUrl(
                            imageList.stream()
                                    .filter(image -> image.getType().equals(ImageType.ADVERTISE))
                                    .map(ProductImage::getUrl)
                                    .collect(Collectors.toList())
                    );

                    cardDTO.setLabelList(new ArrayList<>(labelDAO.findLabelByProductId(product.getId())));
                    cardDTO.setPrice(product.getListedPrice());

                    Discount discountInDate = discountDAO.findDiscountByProductIdUseInDate(product.getId());
                    cardDTO.setDiscountPrice(getDiscountPrice(discountInDate, product));
                    if (discountInDate != null) cardDTO.setSaleOffEndDate(discountInDate.getEndedDate());

                    return cardDTO;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public ProductDetailDTO getProductDetail(Product product) {
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProduct(product);
        dto.setBrandName(brandDAO.findById(product.getBrandId()).getName());
        dto.setCategoryName(categoryDAO.findById(product.getCategoryId()).getName());
        dto.setLabelList((List<Label>) labelDAO.findLabelByProductId(product.getId()));
        dto.setImageList((List<ProductImage>) productImageDAO.findProductImageByProductId(product.getId()));
        dto.setAccessories((List<Product>) productDAO.findAccessoryByProductId(product.getId()));

        Discount discountInDate = discountDAO.findDiscountByProductIdUseInDate(product.getId());
        dto.setDiscountPrice(getDiscountPrice(discountInDate, product));
        return dto;
    }

    @Override
    public Set<Product> findAccessoryOfProduct(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return new HashSet<>(productDAO.findAccessoryByProductId(productId));
    }

    @Override
    public Collection<Product> findProductByBrandId(long brandId) {
        if (brandDAO.findById(brandId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find brand with id=" + brandId);
        return productDAO.findProductByBrandId(brandId);
    }

    @Override
    public Collection<Product> findProductByCategoryId(long categoryId) {
        if (categoryDAO.findById(categoryId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find category with id=" + categoryId);
        return productDAO.findProductByCategoryId(categoryId);
    }
}
