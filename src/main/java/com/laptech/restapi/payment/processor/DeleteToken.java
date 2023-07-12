package com.laptech.restapi.payment.processor;

import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.models.DeleteTokenRequest;
import com.laptech.restapi.payment.models.DeleteTokenResponse;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteToken extends AbstractProcess<DeleteTokenRequest, DeleteTokenResponse> {
    public DeleteToken(Environment environment) {
        super(environment);
    }

    public static DeleteTokenResponse process(Environment env, String requestId, String orderId, String partnerClientId, String token) {
        try {
            DeleteToken m2Processor = new DeleteToken(env);
            DeleteTokenRequest request = m2Processor.createDeleteTokenRequest(orderId, requestId, partnerClientId, token);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[DeleteTokenProcess] " + exception);
        }
        return null;
    }

    @Override
    public DeleteTokenResponse execute(DeleteTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, DeleteTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenDeleteUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[DeleteTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }

            log.info("uweryei7rye8wyreow8: " + response.getData());

            DeleteTokenResponse deleteTokenResponse = getGson().fromJson(response.getData(), DeleteTokenResponse.class);
            String responseRawData = Parameter.REQUEST_ID + "=" +
                    "&" + Parameter.ORDER_ID + "=" + deleteTokenResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + deleteTokenResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + deleteTokenResponse.getResultCode();

            log.info("[DeleteTokenResponse] rawData: " + responseRawData);

            return deleteTokenResponse;

        } catch (Exception exception) {
            log.error("[DeleteTokenResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public DeleteTokenRequest createDeleteTokenRequest(String orderId, String requestId, String partnerClientId, String token) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CLIENT_ID + "=" + partnerClientId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.TOKEN + "=" + token;
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[DeleteTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new DeleteTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, token, signRequest);
        } catch (Exception e) {
            log.error("[DeleteTokenRequest] " + e);
        }
        return null;
    }
}
