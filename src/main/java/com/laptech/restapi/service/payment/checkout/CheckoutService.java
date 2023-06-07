package com.laptech.restapi.service.payment.checkout;

import com.laptech.restapi.dto.request.CheckoutDTO;

/**
 * @since 2023-06-01
 */
public interface CheckoutService {
    void checkout(CheckoutDTO checkoutDTO);
}
