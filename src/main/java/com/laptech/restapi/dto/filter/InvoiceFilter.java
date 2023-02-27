package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class InvoiceFilter extends BaseFilter {
    private Long userId;
    private String address;
    private String phone;
    private BigDecimal paymentAmount;
    private Double shipCost;
    private BigDecimal discountAmount;
    private BigDecimal tax;
    private BigDecimal paymentTotal;
    private String paymentType;
    private Boolean isPaid;
    private OrderStatus orderStatus;
    private String note;

    public void setOrderStatus(String orderStatus) {
        try {
            this.orderStatus = OrderStatus.valueOf(orderStatus);
        } catch (IllegalArgumentException err) {
            this.orderStatus = null;
        }
    }

    public InvoiceFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                         LocalDate deletedDate, Boolean isDel, String updateBy, Long userId, String address,
                         String phone, BigDecimal paymentAmount, Double shipCost, BigDecimal discountAmount,
                         BigDecimal tax, BigDecimal paymentTotal, String paymentType, Boolean isPaid,
                         String orderStatus, String note) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
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
        this.setOrderStatus(orderStatus);
        this.note = note;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.userId);
        objects.add(this.address);
        objects.add(this.phone);
        objects.add(this.paymentAmount);
        objects.add(this.shipCost);
        objects.add(this.discountAmount);
        objects.add(this.tax);
        objects.add(this.paymentTotal);
        objects.add(this.paymentType);
        objects.add(this.isPaid);
        objects.add(this.orderStatus.toString());
        objects.add(this.note);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
