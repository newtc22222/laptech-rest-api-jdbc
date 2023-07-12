package com.laptech.restapi.service.payment.momo;

import com.laptech.restapi.dto.request.PaymentDTO;

import javax.servlet.http.HttpServletRequest;

public interface MomoService {
    String createMomoPaymentLink(PaymentDTO dto, HttpServletRequest request);
}
