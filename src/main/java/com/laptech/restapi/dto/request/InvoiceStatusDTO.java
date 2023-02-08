package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceStatusDTO {
    private String id;
    private OrderStatus status;
    private String paymentType;
    private boolean isPaid;
    private String note;
    private String troubleReason;
}

