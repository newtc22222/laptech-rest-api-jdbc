package com.laptech.restapi.dto.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laptech.restapi.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
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
public class UserFilter extends BaseFilter {
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String email;
    private Boolean isActive;

    public UserFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                      LocalDate deletedDate, Boolean isDel, String updateBy, String name, Gender gender,
                      LocalDate dateOfBirth, String email, Boolean isActive) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.isActive = isActive;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.name);
        objects.add(this.gender.toString());
        objects.add(Date.valueOf(this.dateOfBirth));
        objects.add(this.email);
        objects.add(this.isActive);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
