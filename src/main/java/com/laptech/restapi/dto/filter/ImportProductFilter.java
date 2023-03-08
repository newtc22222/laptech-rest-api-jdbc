package com.laptech.restapi.dto.filter;

import com.laptech.restapi.util.ConvertDate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class ImportProductFilter extends BaseFilter {
    private String productId;
    private Long quantity;
    private BigDecimal importedPrice;
    private LocalDate importedDate;

    public ImportProductFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                               LocalDate deletedDate, Boolean isDel, String updateBy, String productId,
                               Long quantity, BigDecimal importedPrice, LocalDate importedDate) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.productId = productId;
        this.quantity = quantity;
        this.importedPrice = importedPrice;
        this.importedDate = importedDate;
    }

    public ImportProductFilter(Map<String, String> params) {
        super(params);
        this.productId = params.get("productId");
        this.quantity = (params.get("quantity") != null) ? Long.parseLong(params.get("quantity")) : null;
        this.importedPrice = (params.get("importedPrice") != null) ? new BigDecimal(params.get("importedPrice")) : null;
        this.importedDate = ConvertDate.getLocalDateFromString(params.get("importedDate"));
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.productId);
        objects.add(this.quantity);
        objects.add(this.importedPrice);
        objects.add(Date.valueOf(this.importedDate));
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
