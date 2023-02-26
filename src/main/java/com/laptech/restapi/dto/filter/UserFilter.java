package com.laptech.restapi.dto.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laptech.restapi.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private boolean isActive;

    public UserFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                      LocalDate deletedDate, Boolean isDel, String updateBy, String name, Gender gender,
                      LocalDate dateOfBirth, String email, boolean isActive) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.isActive = isActive;
    }
}
