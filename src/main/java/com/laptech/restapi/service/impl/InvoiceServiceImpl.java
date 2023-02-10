package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.InvoiceDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDAO invoiceDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Invoice insert(Invoice invoice) {
        // check

        if (invoiceDAO.isExists(invoice)) {
            throw new ResourceAlreadyExistsException("[Info] This invoice has already existed in system!");
        }
        String newInvoiceId = invoiceDAO.insert(invoice);
        if (newInvoiceId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new invoice!");
        }
        return invoiceDAO.findById(newInvoiceId);
    }

    @Override
    public void update(Invoice invoice, String invoiceId) {
        // check

        Invoice oldInvoice = invoiceDAO.findById(invoiceId);
        if (oldInvoice == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        } else {
            oldInvoice.setUserId(invoice.getUserId());
            oldInvoice.setAddress(invoice.getAddress());
            oldInvoice.setPhone(invoice.getPhone());
            oldInvoice.setPaymentAmount(invoice.getPaymentAmount());
            oldInvoice.setShipCost(invoice.getShipCost());
            oldInvoice.setDiscountAmount(invoice.getDiscountAmount());
            oldInvoice.setTax(invoice.getTax());
            oldInvoice.setPaymentTotal(invoice.getPaymentTotal());
            oldInvoice.setPaymentType(invoice.getPaymentType());
            oldInvoice.setPaid(invoice.isPaid());
            oldInvoice.setOrderStatus(invoice.getOrderStatus());
            oldInvoice.setNote(invoice.getNote());

            if (invoiceDAO.update(invoice) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this invoice!");
            }
        }
    }

    @Override
    public void delete(String invoiceId) {
        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        } else {
            if (invoiceDAO.delete(invoiceId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this invoice!");
            }
        }
    }

    @Override
    public List<Invoice> findAll(Long page, Long size) {
        if (size == null)
            return invoiceDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return invoiceDAO.findAll(limit, skip);
    }

    @Override
    public Invoice findById(String invoiceId) {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        }
//        User user = AuthenticateUserUtil.getCurrentUser();
//        if(user.getId() != invoice.getUserId()) {
//            throw new ForbiddenException("[Info] You can't have grant for access to this invoice!");
//        }
        return invoice;
    }

    @Override
    public void updateStatus(String invoiceId, OrderStatus status) {
        // check

        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        } else {
            if (invoiceDAO.updateStatus(invoiceId, status) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update status for this invoice!");
            }
        }
    }

    @Override
    public void updateInvoicePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid) {
        // check

        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        } else {
            if (invoiceDAO.updatePaymentMethodAndPaidStatus(invoiceId, paymentType, isPaid) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update payment method and paid status for this invoice!");
            }
        }
    }

    @Override
    public List<Invoice> getInvoicesOfUser(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        return invoiceDAO.findInvoiceByUserId(userId);
    }

    @Override
    public Set<Invoice> filter(Map<String, String> params) {
        Set<Invoice> invoiceSet = new HashSet<>(invoiceDAO.findAll());

        if (params.containsKey("address")) {
            List<Invoice> invoiceList = invoiceDAO.findInvoiceByAddress(params.get("address"));
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        if (params.containsKey("date")) {
            List<Invoice> invoiceList = invoiceDAO.findInvoiceByDate(
                    LocalDate.parse(params.get("date"), DateTimeFormatter.ISO_LOCAL_DATE));
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        if (params.containsKey("startDate") && params.containsKey("endDate")) {
            List<Invoice> invoiceList = invoiceDAO.findInvoiceByDateRange(
                    LocalDateTime.parse(params.get("startDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    LocalDateTime.parse(params.get("endDate"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        if (params.containsKey("paymentType")) {
            List<Invoice> invoiceList = invoiceDAO.findInvoiceByPaymentType(params.get("paymentType"));
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        if (params.containsKey("status")) {
            List<Invoice> invoiceList = invoiceDAO.findInvoiceByOrderStatus(OrderStatus.valueOf(params.get("status")));
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        if (params.containsKey("isPaid")) {
            List<Invoice> invoiceList = invoiceDAO.findInvoiceByPaidStatus(Boolean.parseBoolean(params.get("isPaid")));
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }

        return invoiceSet;
    }
}
