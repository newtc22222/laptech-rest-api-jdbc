package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * "hashtag" for product (exp: core i5, amd, led-rgb, ...)
 *
 * @author Nhat Phi
 * @since 2023-02-02
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Label extends SetupDate {
    private long id;
    private String name;
    private String icon;
    private String title;
    private String description;

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
