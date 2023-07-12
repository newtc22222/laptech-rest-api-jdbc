package com.laptech.restapi.payment.processor;

import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.models.PaymentResponse;
import com.laptech.restapi.payment.models.PaymentTokenRequest;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayByToken extends AbstractProcess<PaymentTokenRequest, PaymentResponse> {
    public PayByToken(Environment environment) {
        super(environment);
    }

    public static PaymentResponse process(Environment env, String orderId, String requestId, String amount, String orderInfo,
                                          String returnUrl, String notifyUrl, String extraData, String partnerClientId, String token, Boolean autoCapture) {
        try {
            PayByToken m2Processor = new PayByToken(env);
            PaymentTokenRequest request = m2Processor.createTokenPaymentRequest(orderId, requestId, amount, orderInfo,
                    returnUrl, notifyUrl, extraData, partnerClientId, token, autoCapture);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[PayByTokenProcess] " + exception);
        }
        return null;
    }

    @Override
    public PaymentResponse execute(PaymentTokenRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, PaymentTokenRequest.class);
            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenPayUrl(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[PaymentResponse] [" + request.getOrderId() + "] -> Error API");
            }
            log.info("uweryei7rye8wyreow8: " + response.getData());
            PaymentResponse paymentResponse = getGson().fromJson(response.getData(), PaymentResponse.class);
            String responseRawData = Parameter.REQUEST_ID + "=" + paymentResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + paymentResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + paymentResponse.getMessage() +
                    "&" + Parameter.PAY_URL + "=" + paymentResponse.getPayUrl() +
                    "&" + Parameter.RESULT_CODE + "=" + paymentResponse.getResultCode();
            log.info("[PaymentMoMoResponse] rawData: " + responseRawData);
            return paymentResponse;
        } catch (Exception exception) {
            log.error("[PaymentMoMoResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public PaymentTokenRequest createTokenPaymentRequest(String orderId, String requestId, String amount, String orderInfo,
                                                         String returnUrl, String notifyUrl, String extraData, String partnerClientId, String token, Boolean autoCapture) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.AMOUNT + "=" + amount + "&" +
                    Parameter.EXTRA_DATA + "=" + extraData + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.ORDER_INFO + "=" + orderInfo + "&" +
                    Parameter.PARTNER_CLIENT_ID + "=" + partnerClientId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.TOKEN + "=" + token;
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[PaymentTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new PaymentTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, "MoMo Store", partnerClientId, token, Long.valueOf(amount), "test StoreId",
                    returnUrl, notifyUrl, orderInfo, extraData, autoCapture, null, signRequest);
        } catch (Exception e) {
            log.error("[PaymentTokenRequest] " + e);
        }
        return null;
    }
}
