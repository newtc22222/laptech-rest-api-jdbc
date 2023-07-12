package com.laptech.restapi.payment.processor;

import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.models.RefundMoMoRequest;
import com.laptech.restapi.payment.models.RefundMoMoResponse;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RefundTransaction extends AbstractProcess<RefundMoMoRequest, RefundMoMoResponse> {
    public RefundTransaction(Environment environment) {
        super(environment);
    }

    public static RefundMoMoResponse process(Environment env, String orderId, String requestId, String amount, Long transId, String description) throws Exception {
        try {
            RefundTransaction m2Processor = new RefundTransaction(env);
            RefundMoMoRequest request = m2Processor.createRefundTransactionRequest(orderId, requestId, amount, transId, description);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[RefundTransactionProcess] " + exception);
        }
        return null;
    }

    @Override
    public RefundMoMoResponse execute(RefundMoMoRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, RefundMoMoRequest.class);
            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getRefundUrl(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[RefundResponse] [" + request.getOrderId() + "] -> Error API");
            }
            log.debug("uweryei7rye8wyreow8: " + response.getData());
            RefundMoMoResponse refundMoMoResponse = getGson().fromJson(response.getData(), RefundMoMoResponse.class);
            String responseRawData = Parameter.REQUEST_ID + "=" + refundMoMoResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + refundMoMoResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + refundMoMoResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + refundMoMoResponse.getResultCode();
            log.info("[RefundResponse] rawData: " + responseRawData);
            return refundMoMoResponse;
        } catch (Exception exception) {
            log.error("[RefundResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public RefundMoMoRequest createRefundTransactionRequest(String orderId, String requestId, String amount, Long transId, String description) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.AMOUNT + "=" + amount + "&" +
                    Parameter.DESCRIPTION + "=" + description + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.TRANS_ID + "=" + transId;
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[RefundRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new RefundMoMoRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, Long.valueOf(amount), transId, signRequest, description);
        } catch (Exception e) {
            log.error("[RefundResponse] " + e);
        }
        return null;
    }
}
