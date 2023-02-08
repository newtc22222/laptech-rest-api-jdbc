package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Include: created_date, modified_date
 *
 * @author Nhat Phi
 * @since 2023-02-02
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SetupDate {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Override
    public String toString() {
        return "SetupDate{" +
                "createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
