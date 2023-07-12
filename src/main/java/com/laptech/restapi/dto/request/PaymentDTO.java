package com.laptech.restapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String orderInfo;
    private Long amount;
    private String returnUrl;
    private String extraData;
    private Integer type;
}
