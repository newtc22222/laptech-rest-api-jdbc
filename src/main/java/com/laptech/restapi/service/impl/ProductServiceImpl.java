package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.*;
import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.dto.response.ProductCardDTO;
import com.laptech.restapi.dto.response.ProductDetailDTO;
import com.laptech.restapi.model.Discount;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.ProductImage;
import com.laptech.restapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private BrandDAO brandDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private DiscountDAO discountDAO;
    @Autowired
    private LabelDAO labelDAO;
    @Autowired
    private ProductImageDAO productImageDAO;

    @Autowired
    private ProductAccessoryDAO productAccessoryDAO;
    @Autowired
    private ProductDiscountDAO productDiscountDAO;
    @Autowired
    private ProductLabelDAO productLabelDAO;

    @Override
    public Product insert(Product product) {
        // check

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

            if (productDAO.update(oldProduct) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this product!");
            }
        }
    }

    @Override
    public void delete(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        } else {
            if (productDAO.delete(productId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this product!");
            }
        }
    }

    @Override
    public List<Product> findAll(Long page, Long size) {
        if (size == null)
            return productDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return productDAO.findAll(limit, skip);
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
            if (productDAO.updatePrice(productPriceDTO) == 0) {
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

                    List<ProductImage> imageList = productImageDAO.findProductImageByProductIdAndImageType(product.getId(), ImageType.ADVERTISE);
                    cardDTO.setImagesRepresentUrl(imageList.stream().map(ProductImage::getUrl).collect(Collectors.toList()));
                    cardDTO.setLabelList(labelDAO.findLabelByProductId(product.getId()));
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
        dto.setLabelList(labelDAO.findLabelByProductId(product.getId()));
        dto.setImageList(productImageDAO.findProductImageByProductId(product.getId()));
        dto.setAccessories(productDAO.findAccessoryByProductId(product.getId()));

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
    public List<Product> findProductByBrandId(long brandId) {
        if (brandDAO.findById(brandId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find brand with id=" + brandId);
        return productDAO.findProductByBrandId(brandId);
    }

    @Override
    public List<Product> findProductByCategoryId(long categoryId) {
        if (categoryDAO.findById(categoryId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find category with id=" + categoryId);
        return productDAO.findProductByCategoryId(categoryId);
    }

    @Override
    public Set<Product> filter(Map<String, Object> params) {
        Set<Product> productSet = new HashSet<>(productDAO.findAll());

        if (params.containsKey("name")) {
            List<Product> productList = productDAO.findProductByName(params.get("name").toString());
            productSet.removeIf(item -> !productList.contains(item));
        }
        if (params.containsKey("brandId")) {
            List<String> brandIdList = (List<String>) params.get("brandId");
            List<Product> productList = new ArrayList<>();
            brandIdList.forEach(brandId -> productList.addAll(
                    productDAO.findProductByBrandId(Long.parseLong(brandId))
            ));
            productSet.removeIf(item -> !productList.contains(item));
        }
        if (params.containsKey("categoryId")) {
            List<String> categoryIdList = (List<String>) params.get("categoryId");
            List<Product> productList = new ArrayList<>();
            categoryIdList.forEach(categoryId -> productList.addAll(
                    productDAO.findProductByCategoryId(Long.parseLong(categoryId))
            ));
            productSet.removeIf(item -> !productList.contains(item));
        }
        if (params.containsKey("releasedYear")) {
            List<Product> productList =
                    productDAO.findProductByReleasedYear(Integer.parseInt(String.valueOf(params.get("releasedYear"))));
            productSet.removeIf(item -> !productList.contains(item));
        }
        if (params.containsKey("startPrice") && params.containsKey("endPrice")) {
            List<Product> productList = productDAO.findProductByPriceRange(
                    new BigDecimal(String.valueOf(params.get("startPrice"))),
                    new BigDecimal(String.valueOf(params.get("endPrice")))
            );
            productSet.removeIf(item -> !productList.contains(item));
        }
        if (params.containsKey("label")) {
            List<String> labelIdList = (List<String>) params.get("label");
            List<Product> productList = new ArrayList<>();
            labelIdList.forEach(labelId -> productList.addAll(
                    productDAO.findProductByLabel(Long.parseLong(labelId))
            ));
            productSet.removeIf(item -> !productList.contains(item));
        }
        return productSet;
    }
}
