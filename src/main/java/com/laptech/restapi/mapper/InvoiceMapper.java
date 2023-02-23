package com.laptech.restapi.mapper;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.util.ConvertBaseModel;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class InvoiceMapper implements RowMapper<Invoice> {
    @Override
    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getString("id"));
        invoice.setUserId(rs.getLong("user_id"));
        invoice.setAddress(rs.getNString("address"));
        invoice.setPhone(rs.getString("phone"));
        invoice.setPaymentAmount(rs.getBigDecimal("payment_amount"));
        invoice.setShipCost(rs.getDouble("ship_cost"));
        invoice.setDiscountAmount(rs.getBigDecimal("discount_amount"));
        invoice.setTax(rs.getBigDecimal("tax"));
        invoice.setPaymentTotal(rs.getBigDecimal("payment_total"));
        invoice.setPaymentType(rs.getNString("payment_type"));
        invoice.setPaid(rs.getBoolean("is_paid"));
        invoice.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
        invoice.setNote(rs.getNString("note"));
        invoice.setData(ConvertBaseModel.getBaseModel(rs));
        return invoice;
    }
}
