package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends SetupDate {
    private long id;
    private String name;
    private String country;
    private LocalDate establishDate;
    private String logo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return id == brand.id
                && name.equals(brand.name)
                && country.equals(brand.country)
                && Objects.equals(establishDate, brand.establishDate)
                && logo.equals(brand.logo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", establishDate=" + establishDate +
                ", logo='" + logo + '\'' +
                "} " + super.toString();
    }
}
