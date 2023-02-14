package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h3>Note</h3>
 * <ul>
 *     <li>Country: default - "Việt Nam"</li>
 *     <li>line1: big city, province</li>
 *     <li>line2: city, district, town</li>
 *     <li>line3: commune</li>
 * </ul>
 *
 * @author Nhat Phi
 * @since 2022-11-08 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends SetupDate {
    private String id;
    private long userId;
    private String country;
    private String line1;
    private String line2;
    private String line3;
    private String street;
    private boolean isDefault;

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", country='" + country + '\'' +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", street='" + street + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", '" + super.toString() + '\'' +
                '}';
    }
}
