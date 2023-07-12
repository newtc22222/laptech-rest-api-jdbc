package com.laptech.restapi.service.payment.momo;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.dto.request.PaymentDTO;
import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.RequestType;
import com.laptech.restapi.payment.models.PaymentResponse;
import com.laptech.restapi.payment.processor.CreateOrderMoMo;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MomoServiceImpl implements MomoService{
    @Override
    public String createMomoPaymentLink(PaymentDTO dto, HttpServletRequest request1) {
        Environment environment = Environment.selectEnv("dev");
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        String notifyURL = "https://webhook.site/b451f52e-dc81-435f-a082-ff4b57d72296";
        /*
        * Type 0: Create QR payment link
        * Type 1: Create ATM payment link
        * Type 2: Create MASTER CARD/JBC/VISA payment link
        * Type 3: Create payment link for 3 method above.
        * */
        try {
            PaymentResponse captureWalletMoMoResponse = null;
            if(dto.getType().equals(0)){
                captureWalletMoMoResponse = CreateOrderMoMo.process(environment,
                        orderId,
                        requestId,
                        Long.toString(dto.getAmount()),
                        dto.getOrderInfo(),
                        dto.getReturnUrl(),
                        notifyURL,
                        dto.getExtraData(),
                        RequestType.CAPTURE_WALLET,
                        Boolean.TRUE
                );
            }
            if(dto.getType().equals(1)){
                captureWalletMoMoResponse = CreateOrderMoMo.process(environment,
                        orderId,
                        requestId,
                        Long.toString(dto.getAmount()),
                        dto.getOrderInfo(),
                        dto.getReturnUrl(),
                        notifyURL,
                        dto.getExtraData(),
                        RequestType.PAY_WITH_ATM,
                        Boolean.TRUE
                );
            }
            if(dto.getType().equals(2)){
                captureWalletMoMoResponse = CreateOrderMoMo.process(environment,
                        orderId,
                        requestId,
                        Long.toString(dto.getAmount()),
                        dto.getOrderInfo(),
                        dto.getReturnUrl(),
                        notifyURL,
                        dto.getExtraData(),
                        RequestType.PAY_WITH_CREDIT,
                        Boolean.TRUE
                );
            }
            if(dto.getType().equals(3)){
                captureWalletMoMoResponse = CreateOrderMoMo.process(environment,
                        orderId,
                        requestId,
                        Long.toString(dto.getAmount()),
                        dto.getOrderInfo(),
                        dto.getReturnUrl(),
                        notifyURL,
                        dto.getExtraData(),
                        RequestType.PAY_WITH_METHOD,
                        Boolean.TRUE
                );
            }
            return captureWalletMoMoResponse.getPayUrl();
        } catch (Exception exception) {
            throw new InternalServerErrorException(exception.getMessage());
        }
    }
}
