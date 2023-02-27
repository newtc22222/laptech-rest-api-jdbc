package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.enums.ImageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class ProductImageFilter extends BaseFilter{
    private String productId;
    private String feedbackId;
    private String url;
    private ImageType type;

    public ProductImageFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                              LocalDate deletedDate, Boolean isDel, String updateBy, String productId,
                              String feedbackId, String url, ImageType type) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.productId = productId;
        this.feedbackId = feedbackId;
        this.url = url;
        this.type = type;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.productId);
        objects.add(this.feedbackId);
        objects.add(this.url);
        objects.add(this.type);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
