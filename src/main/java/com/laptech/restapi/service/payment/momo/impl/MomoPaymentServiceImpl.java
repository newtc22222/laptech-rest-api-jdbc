package com.laptech.restapi.service.payment.momo.impl;

import com.laptech.restapi.dto.request.payment.MomoPaymentDTO;
import com.laptech.restapi.service.payment.momo.MomoPaymentService;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.LogUtils;
import org.springframework.stereotype.Service;

/**
 * @since 2023-03-05
 */
@Service
public class MomoPaymentServiceImpl implements MomoPaymentService {
    @Override
    public PaymentResponse createPayment(MomoPaymentDTO dto) throws Exception {
        LogUtils.init();
        Environment environment = Environment.selectEnv("dev");
        return CreateOrderMoMo.process(
                environment,
                String.valueOf(System.currentTimeMillis()),
                dto.getRequestId(),
                dto.getAmount().toString(),
                dto.getOrderInfo(),
                dto.getReturnUrl(),
                dto.getNotifyUrl(),
                dto.getOrderId(),
                RequestType.CAPTURE_WALLET,
                Boolean.TRUE);
    }
}
