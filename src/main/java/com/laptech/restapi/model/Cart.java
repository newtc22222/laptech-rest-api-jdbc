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
public class Cart extends BaseModel {
    private String id;
    private long userId;
    private Long discountId;

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", discountId=" + discountId +
                "} " + super.toString();
    }
}
