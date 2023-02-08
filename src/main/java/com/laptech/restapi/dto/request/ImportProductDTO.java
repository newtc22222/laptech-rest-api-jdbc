package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.ImportProduct;
import com.laptech.restapi.util.ConvertDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@ApiModel("Class representing for ImportProduct request body")
public class ImportProductDTO {
    @ApiModelProperty(required = true)
    private String productId;
    @ApiModelProperty(required = true)
    private Long quantity;
    @ApiModelProperty(required = true)
    private BigDecimal importedPrice;
    private String importedDate;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getImportedPrice() {
        return importedPrice;
    }

    public void setImportedPrice(BigDecimal importedPrice) {
        this.importedPrice = importedPrice;
    }

    public String getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(String importedDate) {
        this.importedDate = importedDate;
    }

    public static ImportProduct transform(ImportProductDTO importProductDTO) {
        ImportProduct ticket = new ImportProduct();
        ticket.setId(UUID.randomUUID().toString());
        ticket.setProductId(importProductDTO.getProductId());
        ticket.setQuantity(importProductDTO.getQuantity());
        ticket.setImportedPrice(importProductDTO.getImportedPrice());
        if (importProductDTO.getImportedDate() != null) {
            ticket.setImportedDate(ConvertDateTime.getDateTimeFromString(importProductDTO.getImportedDate()));
        }
        return ticket;
    }
}

