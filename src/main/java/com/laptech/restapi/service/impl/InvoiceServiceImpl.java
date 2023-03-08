package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.enums.OrderStatus;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.InvoiceDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dto.filter.InvoiceFilter;
import com.laptech.restapi.model.Invoice;
import com.laptech.restapi.service.InvoiceService;
import com.laptech.restapi.util.ConvertDate;
import com.laptech.restapi.util.ConvertDateTime;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
            oldInvoice.setUpdateBy(invoice.getUpdateBy());

            if (invoiceDAO.update(invoice) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this invoice!");
            }
        }
    }

    @Override
    public void delete(String invoiceId, String updateBy) {
        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        } else {
            if (invoiceDAO.delete(invoiceId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this invoice!");
            }
        }
    }

    @Override
    public long count() {
        return invoiceDAO.count();
    }


    @Override
    public Collection<Invoice> findAll(String sortBy, String sortDir, Long page, Long size) {
        return invoiceDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Invoice> findWithFilter(Map<String, Object> params) {
        InvoiceFilter filter = new InvoiceFilter(ConvertMap.changeAllValueFromObjectToString(params));
        Set<Invoice> invoiceSet = (Set<Invoice>) invoiceDAO.findWithFilter(filter);

        if (params.containsKey("date")) {
            Collection<Invoice> invoiceList = invoiceDAO.findInvoiceByDate(
                    ConvertDate.getLocalDateFromString(params.get("date").toString()));
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        if (params.containsKey("startDate") && params.containsKey("endDate")) {
            Collection<Invoice> invoiceList = invoiceDAO.findInvoiceByDateRange(
                    ConvertDateTime.getDateTimeFromString(params.get("startDate").toString()),
                    ConvertDateTime.getDateTimeFromString(params.get("endedDate").toString())
            );
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        if (params.containsKey("status")) {
            Collection<Invoice> invoiceList = invoiceDAO.findInvoiceByOrderStatus(OrderStatus.valueOf(params.get("status").toString()));
            invoiceSet.removeIf(item -> !invoiceList.contains(item));
        }
        return invoiceSet;
    }

    @Override
    public Invoice findById(String invoiceId) {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        }
        return invoice;
    }

    @Override
    public void updateStatus(String invoiceId, OrderStatus status, String updateBy) {
        // check

        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        } else {
            if (invoiceDAO.updateOrderStatus(invoiceId, status, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update status for this invoice!");
            }
        }
    }

    @Override
    public void updateInvoicePaymentMethodAndPaidStatus(String invoiceId, String paymentType, boolean isPaid, String updateBy) {
        // check

        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        } else {
            if (invoiceDAO.updatePaymentMethodAndPaidStatus(invoiceId, paymentType, isPaid, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update payment method and paid status for this invoice!");
            }
        }
    }

    @Override
    public Collection<Invoice> getInvoicesOfUser(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        return invoiceDAO.findInvoiceByUserId(userId);
    }
}
