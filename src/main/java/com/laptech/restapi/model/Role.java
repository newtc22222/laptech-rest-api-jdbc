package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseModel {
    private int id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
