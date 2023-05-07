package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.model.Invoice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
public class InvoiceDTO {
    private String id;
    @ApiModelProperty(required = true)
    @NotNull
    private Long userId;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(min = 20, max = 255)
    private String address;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(min = 10, max = 15)
    private String phone;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal paymentAmount;

    private Double shipCost;
    private BigDecimal discountAmount;
    private BigDecimal tax;

    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal paymentTotal;
    @ApiModelProperty(required = true, example = "cash | momo | paypal | ...")
    @NotNull
    @Size(max = 50)
    private String paymentType;
    @ApiModelProperty(required = true)
    @NotNull
    private Boolean isPaid;
    @ApiModelProperty(required = true)
    @NotNull
    private String orderStatus;
    private String note;
    @Size(max = 100)
    private String updateBy;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String id, Long userId, String address, String phone, BigDecimal paymentAmount, Double shipCost,
                      BigDecimal discountAmount, BigDecimal tax, BigDecimal paymentTotal, String paymentType,
                      Boolean isPaid, String orderStatus, String note, String updateBy) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.paymentAmount = paymentAmount;
        this.shipCost = shipCost;
        this.discountAmount = discountAmount;
        this.tax = tax;
        this.paymentTotal = paymentTotal;
        this.paymentType = paymentType;
        this.isPaid = isPaid;
        this.orderStatus = orderStatus;
        this.note = note;
        this.updateBy = updateBy;
    }

    public static Invoice transform(InvoiceDTO dto) {
        String newInvoiceId = dto.getId() != null
                ? dto.getId()
                : UUID.randomUUID().toString().replace("-", "").substring(0, 15);

        Invoice invoice = new Invoice();
        invoice.setId(newInvoiceId);
        invoice.setUserId(dto.getUserId());
        invoice.setAddress(dto.getAddress());
        invoice.setPhone(dto.getPhone());
        invoice.setPaymentAmount(dto.getPaymentAmount());
        invoice.setShipCost(dto.getShipCost());
        invoice.setDiscountAmount(dto.getDiscountAmount());
        invoice.setTax(dto.getTax());
        invoice.setPaymentTotal(dto.getPaymentTotal());
        invoice.setPaymentType(dto.getPaymentType());
        invoice.setPaid(dto.getIsPaid());
        invoice.setOrderStatus(OrderStatus.valueOf(dto.getOrderStatus().trim().toUpperCase()));
        invoice.setNote(dto.getNote());
        invoice.setUpdateBy(dto.getUpdateBy());
        return invoice;
    }
}

