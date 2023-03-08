package com.laptech.restapi.service.payment.momo;

import com.laptech.restapi.dto.request.payment.MomoPaymentDTO;
import com.mservice.models.PaymentResponse;

/**
 * @see <a href="https://gitlab.com/hoadaknong/ecom">Gitlab - Hoadaknong - ecom</a>
 * @since 2023-03-05
 */
public interface MomoPaymentService {
    PaymentResponse createPayment(MomoPaymentDTO dto) throws Exception;
}
