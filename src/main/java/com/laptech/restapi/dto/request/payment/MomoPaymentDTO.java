package com.laptech.restapi.dto.request.payment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @since 2023-03-05
 */
@Getter
@Setter
public class MomoPaymentDTO {
    @NotEmpty
    private String orderId;
    private String requestId;
    @NotNull
    private Long amount;
    @NotEmpty
    private String orderInfo;
    private String returnUrl;
    private String notifyUrl;
    private String extraData;

    public MomoPaymentDTO() {}

    public MomoPaymentDTO(String orderId, String requestId, Long amount, String orderInfo, String returnUrl,
                          String notifyUrl, String extraData) {
        this.orderId = orderId;
        this.requestId = (requestId == null || requestId.isEmpty()) ? String.valueOf(System.currentTimeMillis()) : requestId;
        this.amount = amount;
        this.orderInfo = orderInfo;
        this.returnUrl = returnUrl;
        this.notifyUrl = notifyUrl;
        this.extraData = extraData;
    }
}
