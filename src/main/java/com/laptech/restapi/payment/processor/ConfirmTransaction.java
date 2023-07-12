package com.laptech.restapi.payment.processor;


import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.ConfirmRequestType;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.models.ConfirmRequest;
import com.laptech.restapi.payment.models.ConfirmResponse;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfirmTransaction extends AbstractProcess<ConfirmRequest, ConfirmResponse> {
    public ConfirmTransaction(Environment environment) {
        super(environment);
    }

    public static ConfirmResponse process(Environment env, String orderId, String requestId, String amount, ConfirmRequestType requestType, String description) throws Exception {
        try {
            ConfirmTransaction m2Processor = new ConfirmTransaction(env);
            ConfirmRequest request = m2Processor.createConfirmTransactionRequest(orderId, requestId, amount, requestType, description);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[ConfirmTransactionProcess] " + exception);
        }
        return null;
    }

    @Override
    public ConfirmResponse execute(ConfirmRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, ConfirmRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getConfirmUrl(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[ConfirmTransactionResponse] [" + request.getOrderId() + "] -> Error API");
            }
            log.debug("uweryei7rye8wyreow8: " + response.getData());
            ConfirmResponse confirmResponse = getGson().fromJson(response.getData(), ConfirmResponse.class);
            String responseRawData = Parameter.ORDER_ID + "=" + confirmResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + confirmResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + confirmResponse.getResultCode();
            log.debug("[ConfirmTransactionResponse] rawData: " + responseRawData);
            return confirmResponse;
        } catch (Exception exception) {
            log.error("[ConfirmTransactionResponse] " + exception);
            throw new IllegalArgumentException("Invalid params confirm MoMo Request");
        }
    }

    public ConfirmRequest createConfirmTransactionRequest(String orderId, String requestId, String amount, ConfirmRequestType requestType, String description) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.AMOUNT + "=" + amount + "&" +
                    Parameter.DESCRIPTION + "=" + description + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.REQUEST_TYPE + "=" + requestType.getConfirmRequestType();
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[ConfirmRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new ConfirmRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, Long.valueOf(amount), "", ConfirmRequestType.CAPTURE, signRequest);
        } catch (Exception e) {
            log.error("[ConfirmResponse] " + e);
        }
        return null;
    }
}
