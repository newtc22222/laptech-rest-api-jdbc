package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.ImportProduct;
import com.laptech.restapi.util.ConvertDateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
public class ImportProductDTO {
    private String id;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String productId;
    @ApiModelProperty(required = true)
    @NotNull
    private Long quantity;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal importedPrice;
    @ApiModelProperty(example = "2023-03-05T17:00:00:123")
    private String importedDate;
    @Size(max = 100)
    private String updateBy;

    public ImportProductDTO() {}

    public ImportProductDTO(String id, String productId, Long quantity, BigDecimal importedPrice, String importedDate, String updateBy) {
        this.id = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        this.productId = productId;
        this.quantity = quantity;
        this.importedPrice = importedPrice;
        this.importedDate = importedDate;
        this.updateBy = updateBy;
    }

    public static ImportProduct transform(ImportProductDTO dto) {
        ImportProduct ticket = new ImportProduct();
        ticket.setId(dto.getId());
        ticket.setProductId(dto.getProductId());
        ticket.setQuantity(dto.getQuantity());
        ticket.setImportedPrice(dto.getImportedPrice());
        ticket.setImportedDate(ConvertDateTime.getDateTimeFromString(dto.getImportedDate()));
        ticket.setUpdateBy(dto.getUpdateBy());
        return ticket;
    }
}

