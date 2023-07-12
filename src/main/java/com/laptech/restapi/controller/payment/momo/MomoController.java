package com.laptech.restapi.controller.payment.momo;

import com.laptech.restapi.dto.request.PaymentDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.service.InvoiceService;
import com.laptech.restapi.service.payment.momo.MomoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "Momo", description = "Create momo payment links")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class MomoController {
    private final MomoService momoService;
    private final InvoiceService invoiceService;

    @ApiOperation(value = "Momo payment", response = DataResponse.class)
    @PostMapping("/momo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> getLinkPaymentMomo(@RequestBody PaymentDTO dto,
                                                           HttpServletRequest request) {
        return DataResponse.getObjectSuccess(
                "Get link checkout with Momo",
                momoService.createMomoPaymentLink(dto, request)
        );
    }

    @ApiOperation(value = "Checkout momo success", response = BaseResponse.class)
    @PostMapping("/momo-success")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse> checkoutMomoSuccessfully(@RequestBody Map<String, String> checkoutInfo) {
        Invoice invoice = invoiceService.findById(checkoutInfo.get("orderId"));
        invoice.setPaid(true);
        invoice.setPaymentType("Momo");
        invoiceService.update(invoice, invoice.getId());
        return DataResponse.success("Checkout with Momo");
    }
}

// EXAMPLE
//"partnerCode": "MOMO",
//"orderId": "1688656249271", -> invoice_id or random
//"requestId": "1688656249271",
//"amount": 10000000,
//"orderInfo": "string info",
//"orderType": "momo_wallet",
//"transId": 3041892500,
//"resultCode": 0,
//"message": "Successful.",
//"payType": "credit",
//"responseTime": 1688656292944,
//"extraData": "invoice_id",
//"signature": "b226d246357a9af52f61bba75856ee8b1de4898f6678bf8da5cf26185536512a"