package com.laptech.restapi.service.payment.checkout;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.dao.CartDAO;
import com.laptech.restapi.dao.InvoiceDAO;
import com.laptech.restapi.dao.ProductUnitDAO;
import com.laptech.restapi.dto.request.CheckoutDTO;
import com.laptech.restapi.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService{
    private final CartDAO cartDAO;
    private final InvoiceDAO invoiceDAO;
    private final ProductUnitDAO productUnitDAO;

    @Override
    public void checkout(CheckoutDTO checkoutDTO) {
        String newInvoiceId;
        // new user + new invoices
        if (checkoutDTO.getCartId() == null) {
            newInvoiceId = checkoutDTO.getInvoiceId() != null
                    ? checkoutDTO.getInvoiceId()
                    : UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
        }
        // old user has cart
        else {
            newInvoiceId = checkoutDTO.getCartId();
            cartDAO.delete(checkoutDTO.getCartId(), checkoutDTO.getUpdateBy());
        }
        // handle new invoice
        Invoice newInvoice = new Invoice(
                newInvoiceId,
                checkoutDTO.getUserId(),
                checkoutDTO.getAddress(),
                checkoutDTO.getPhone(),
                checkoutDTO.getPaymentAmount(),
                checkoutDTO.getShipCost(),
                checkoutDTO.getDiscountAmount(),
                checkoutDTO.getTax(),
                checkoutDTO.getPaymentTotal(),
                checkoutDTO.getPaymentType(),
                checkoutDTO.getIsPaid(),
                OrderStatus.valueOf(checkoutDTO.getOrderStatus()),
                checkoutDTO.getNote());
        newInvoice.setUpdateBy(checkoutDTO.getUpdateBy());
        if (invoiceDAO.isExists(newInvoice)) {
            throw new ResourceAlreadyExistsException("[Info] This invoice has already existed in system!");
        }
        String newInvoiceIdInsert = invoiceDAO.insert(newInvoice);
        if (newInvoiceIdInsert == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new invoice!");
        }
        checkoutDTO.getProductUnitList().stream().parallel().forEach(productUnit -> {
            productUnit.setCartId(null);
            productUnit.setInvoiceId(newInvoiceIdInsert);
            if (productUnitDAO.isExists(productUnit)) {
                throw new ResourceAlreadyExistsException("[Info] This unit has already existed in system!");
            }
            String newUnitId = productUnitDAO.insert(productUnit);
            if (newUnitId == null) {
                throw new InternalServerErrorException("[Error] Failed to insert new product unit");
            }
        });
    }
}
