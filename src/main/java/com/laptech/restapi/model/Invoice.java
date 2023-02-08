package com.laptech.restapi.model;

import com.laptech.restapi.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends SetupDate {
    private String id;
    private long userId;
    private String address;
    private BigDecimal paymentAmount;
    private Double shipCost;
    private BigDecimal discountAmount;
    private BigDecimal tax;
    private BigDecimal paymentTotal;
    private String paymentType;
    private boolean isPaid;
    private OrderStatus orderStatus;
    private String note;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return userId == invoice.userId
                && isPaid == invoice.isPaid
                && id.equals(invoice.id)
                && address.equals(invoice.address)
                && paymentAmount.equals(invoice.paymentAmount)
                && shipCost.equals(invoice.shipCost)
                && discountAmount.equals(invoice.discountAmount)
                && tax.equals(invoice.tax)
                && paymentTotal.equals(invoice.paymentTotal)
                && paymentType.equals(invoice.paymentType)
                && orderStatus == invoice.orderStatus
                && Objects.equals(note, invoice.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", address='" + address + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", shipCost=" + shipCost +
                ", discountAmount=" + discountAmount +
                ", tax=" + tax +
                ", paymentTotal=" + paymentTotal +
                ", paymentType='" + paymentType + '\'' +
                ", orderStatus=" + orderStatus +
                ", isPaid=" + isPaid +
                ", note='" + note + '\'' +
                "} " + super.toString();
    }
}
