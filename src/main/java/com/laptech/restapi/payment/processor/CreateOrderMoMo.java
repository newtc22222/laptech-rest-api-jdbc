package com.laptech.restapi.payment.processor;


import com.laptech.restapi.payment.config.Environment;
import com.laptech.restapi.payment.enums.Language;
import com.laptech.restapi.payment.enums.RequestType;
import com.laptech.restapi.payment.models.HttpResponse;
import com.laptech.restapi.payment.models.PaymentRequest;
import com.laptech.restapi.payment.models.PaymentResponse;
import com.laptech.restapi.payment.shared.constants.Parameter;
import com.laptech.restapi.payment.shared.exception.MoMoException;
import com.laptech.restapi.payment.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hainguyen
 * Documention: <a href="https://developers.momo.vn">MOMO</a>
 */
@Slf4j
public class CreateOrderMoMo extends AbstractProcess<PaymentRequest, PaymentResponse> {

    public CreateOrderMoMo(Environment environment) {
        super(environment);
    }

    /**
     * Capture MoMo Process on Payment Gateway
     *
     * @param amount    grand total of the bill
     * @param extraData extra data about the bill
     * @param orderInfo information about customer
     * @param env       name of the environment (dev or prod)
     * @param orderId   unique order ID in MoMo system
     * @param requestId request ID
     * @param returnURL URL to redirect customer
     * @param notifyURL URL for MoMo to return transaction status to merchant
     * @return PaymentResponse
     **/

    public static PaymentResponse process(Environment env, String orderId, String requestId, String amount, String orderInfo, String returnURL, String notifyURL, String extraData, RequestType requestType, Boolean autoCapture) throws Exception {
        try {
            CreateOrderMoMo m2Processor = new CreateOrderMoMo(env);
            PaymentRequest request = m2Processor.createPaymentCreationRequest(orderId, requestId, amount, orderInfo, returnURL, notifyURL, extraData, requestType, autoCapture);
            return m2Processor.execute(request);
        } catch (Exception exception) {
            log.error("[CreateOrderMoMoProcess] " + exception);
        }
        return null;
    }

    @Override
    public PaymentResponse execute(PaymentRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, PaymentRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getCreateUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[PaymentResponse] [" + request.getOrderId() + "] -> Error API");
            }
            return getGson().fromJson(response.getData(), PaymentResponse.class);

        } catch (Exception exception) {
            log.error("[PaymentMoMoResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    /**
     * @param orderId   order id
     * @param requestId request id
     * @param amount    grand total of the bill
     * @param orderInfo information about the customer
     * @param returnUrl the URL will return after paying
     * @param notifyUrl the notification after paying
     * @param extraData the extra data about the bill
     * @return PaymentRequest
     */
    public PaymentRequest createPaymentCreationRequest(String orderId, String requestId, String amount, String orderInfo,
                                                       String returnUrl, String notifyUrl, String extraData, RequestType requestType, Boolean autoCapture) {

        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.AMOUNT + "=" + amount + "&" +
                    Parameter.EXTRA_DATA + "=" + extraData + "&" +
                    Parameter.IPN_URL + "=" + notifyUrl + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.ORDER_INFO + "=" + orderInfo + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REDIRECT_URL + "=" + returnUrl + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.REQUEST_TYPE + "=" + requestType.getRequestType();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            log.info("[PaymentRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);
            return new PaymentRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, orderInfo, Long.parseLong(amount), "test MoMo", null, requestType,
                    returnUrl, notifyUrl, "test store ID", extraData, null, autoCapture, null, signRequest);
        } catch (Exception e) {
            log.error("[PaymentRequest] " + e);
        }

        return null;
    }

}
