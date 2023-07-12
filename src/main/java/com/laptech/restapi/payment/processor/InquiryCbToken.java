package com.laptech.restapi.payment.processor;


import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.models.CbTokenInquiryRequest;
import com.laptech.restapi.payment.models.CbTokenInquiryResponse;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InquiryCbToken extends AbstractProcess<CbTokenInquiryRequest, CbTokenInquiryResponse> {
    public InquiryCbToken(Environment environment) {
        super(environment);
    }

    public static CbTokenInquiryResponse process(Environment env, String orderId, String requestId, String partnerClientId) throws Exception {
        try {
            InquiryCbToken m2Processor = new InquiryCbToken(env);
            CbTokenInquiryRequest request = m2Processor.createInquiryTokenRequest(orderId, requestId, partnerClientId);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[TokenInquiryProcess] " + exception);
        }
        return null;
    }

    @Override
    public CbTokenInquiryResponse execute(CbTokenInquiryRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, CbTokenInquiryRequest.class);
            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getCbTokenInquiryUrl(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[CbTokenInquiryResponse] [" + request.getOrderId() + "] -> Error API");
            }
            log.debug("uweryei7rye8wyreow8: " + response.getData());
            CbTokenInquiryResponse cbTokenInquiryResponse = getGson().fromJson(response.getData(), CbTokenInquiryResponse.class);
            String responseRawData = Parameter.REQUEST_ID + "=" + cbTokenInquiryResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + cbTokenInquiryResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + cbTokenInquiryResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + cbTokenInquiryResponse.getResultCode();
            log.info("[CbTokenInquiryResponse] rawData: " + responseRawData);
            return cbTokenInquiryResponse;
        } catch (Exception exception) {
            log.error("[CbTokenInquiryResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public CbTokenInquiryRequest createInquiryTokenRequest(String orderId, String requestId, String partnerClientId) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CLIENT_ID + "=" + partnerClientId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId;
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[TokenInquiryRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new CbTokenInquiryRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, signRequest);
        } catch (Exception e) {
            log.error("[TokenInquiryResponse] " + e);
        }
        return null;
    }
}
