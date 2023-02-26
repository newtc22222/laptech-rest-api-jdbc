package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
@Getter @Setter
public class AddressFilter extends BaseFilter {
    private Long userId;
    private String country;
    private String line1;
    private String line2;
    private String line3;
    private String street;
    private Boolean isDefault;

    public AddressFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                         LocalDate deletedDate, Boolean isDel, String updateBy, Long userId, String country,
                         String line1, String line2, String line3, String street, Boolean isDefault) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.userId = userId;
        this.country = country;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.street = street;
        this.isDefault = isDefault;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.userId);
        objects.add(this.country);
        objects.add(this.line1);
        objects.add(this.line2);
        objects.add(this.line3);
        objects.add(this.street);
        objects.add(this.isDefault);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
