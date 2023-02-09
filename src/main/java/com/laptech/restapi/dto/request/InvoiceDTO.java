package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.model.Invoice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
@ApiModel("Class representing for invoice request body")
public class InvoiceDTO {
    @ApiModelProperty(required = true)
    private Long userId;
    @ApiModelProperty(required = true)
    private String address;
    @ApiModelProperty(required = true)
    private String phone;
    @ApiModelProperty(required = true)
    private BigDecimal paymentAmount;

    private Double shipCost;
    private BigDecimal discountAmount;
    private BigDecimal tax;
    @ApiModelProperty(required = true)
    private BigDecimal paymentTotal;
    @ApiModelProperty(required = true, example = "direct | momo | paypal | ...")
    private String paymentType;
    @ApiModelProperty(required = true)
    private boolean isPaid;
    @ApiModelProperty(required = true)
    private String orderStatus;
    private String note;


    public static Invoice transform(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setId(UUID.randomUUID().toString());
        invoice.setUserId(invoiceDTO.getUserId());
        invoice.setAddress(invoiceDTO.getAddress());
        invoice.setPhone(invoiceDTO.getPhone());
        invoice.setPaymentAmount(invoiceDTO.getPaymentAmount());
        invoice.setShipCost(invoiceDTO.getShipCost());
        invoice.setDiscountAmount(invoiceDTO.getDiscountAmount());
        invoice.setTax(invoiceDTO.getTax());
        invoice.setPaymentTotal(invoiceDTO.getPaymentTotal());
        invoice.setPaymentType(invoiceDTO.getPaymentType());
        invoice.setPaid(invoiceDTO.isPaid());
        invoice.setOrderStatus(OrderStatus.valueOf(invoiceDTO.getOrderStatus().trim().toUpperCase()));
        invoice.setNote(invoiceDTO.getNote());
        return invoice;
    }
}

