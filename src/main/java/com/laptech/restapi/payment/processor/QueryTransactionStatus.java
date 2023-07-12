package com.laptech.restapi.payment.processor;

import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.models.QueryStatusTransactionRequest;
import com.laptech.restapi.payment.models.QueryStatusTransactionResponse;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryTransactionStatus extends AbstractProcess<QueryStatusTransactionRequest, QueryStatusTransactionResponse> {
    public QueryTransactionStatus(Environment environment) {
        super(environment);
    }

    public static QueryStatusTransactionResponse process(Environment env, String orderId, String requestId) throws Exception {
        try {
            QueryTransactionStatus m2Processor = new QueryTransactionStatus(env);
            QueryStatusTransactionRequest request = m2Processor.createQueryTransactionRequest(orderId, requestId);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[QueryTransactionProcess] " + exception);
        }
        return null;
    }

    @Override
    public QueryStatusTransactionResponse execute(QueryStatusTransactionRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, QueryStatusTransactionRequest.class);
            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getQueryUrl(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[QueryTransactionResponse] [" + request.getOrderId() + "] -> Error API");
            }
            log.debug("uweryei7rye8wyreow8: " + response.getData());
            QueryStatusTransactionResponse queryStatusTransactionResponse = getGson().fromJson(response.getData(), QueryStatusTransactionResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + queryStatusTransactionResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + queryStatusTransactionResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + queryStatusTransactionResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + queryStatusTransactionResponse.getResultCode();
            log.debug("[QueryTransactionResponse] rawData: " + responserawData);
            return queryStatusTransactionResponse;

        } catch (Exception exception) {
            log.error("[QueryTransactionResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public QueryStatusTransactionRequest createQueryTransactionRequest(String orderId, String requestId) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId;
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.debug("[QueryTransactionRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new QueryStatusTransactionRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, signRequest);
        } catch (Exception e) {
            log.error("[QueryTransactionRequest] " + e);
        }
        return null;
    }
}
