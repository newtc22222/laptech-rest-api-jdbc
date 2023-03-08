package com.laptech.restapi.controller.payment;

import com.laptech.restapi.dto.request.payment.MomoPaymentDTO;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.service.payment.momo.MomoPaymentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @since 2023-03-05
 */
@Api(tags = "Payment Controller")
@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
public class PaymentController {
    @Autowired
    private MomoPaymentService paymentService;

    @PostMapping("/momo")
    public ResponseEntity<DataResponse> createMomoPayment(@RequestBody MomoPaymentDTO dto) throws Exception{
        return DataResponse.success(
                "Create Momo payment",
                paymentService.createPayment(dto)
        );
    }
}
