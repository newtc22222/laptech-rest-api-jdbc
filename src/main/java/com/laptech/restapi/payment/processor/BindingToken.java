package com.laptech.restapi.payment.processor;


import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.models.BindingTokenRequest;
import com.laptech.restapi.payment.models.BindingTokenResponse;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BindingToken extends AbstractProcess<BindingTokenRequest, BindingTokenResponse> {
    public BindingToken(Environment environment) {
        super(environment);
    }

    public static BindingTokenResponse process(Environment env, String orderId, String requestId, String partnerClientId, String callbackToken) {
        try {
            BindingToken m2Processor = new BindingToken(env);
            BindingTokenRequest request = m2Processor.createBindingTokenRequest(orderId, requestId, partnerClientId, callbackToken);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[BindingTokenProcess] " + exception);
        }
        return null;
    }

    @Override
    public BindingTokenResponse execute(BindingTokenRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, BindingTokenRequest.class);
            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenBindUrl(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[BindingTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }
            log.debug("uweryei7rye8wyreow8: " + response.getData());
            BindingTokenResponse BindingTokenResponse = getGson().fromJson(response.getData(), BindingTokenResponse.class);
            String responseRawData = Parameter.REQUEST_ID + "=" + BindingTokenResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + BindingTokenResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + BindingTokenResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + BindingTokenResponse.getResultCode();
            log.debug("[BindingTokenResponse] rawData: " + responseRawData);
            return BindingTokenResponse;
        } catch (Exception exception) {
            log.error("[BindingTokenResponse] " + exception);
            throw new IllegalArgumentException("Tham số không hợp lệ!");
        }
    }

    public BindingTokenRequest createBindingTokenRequest(String orderId, String requestId, String partnerClientId, String callbackToken) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.CALLBACK_TOKEN + "=" + callbackToken + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CLIENT_ID + "=" + partnerClientId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId;
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[BindingTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new BindingTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, callbackToken, signRequest);
        } catch (Exception e) {
            log.error("[BindingTokenResponse] " + e);
        }

        return null;
    }

}
