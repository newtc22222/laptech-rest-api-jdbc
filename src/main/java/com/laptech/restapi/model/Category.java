package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nhat Phi
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends SetupDate {
    private long id;
    private String name;
    private String image;
    private String description;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
