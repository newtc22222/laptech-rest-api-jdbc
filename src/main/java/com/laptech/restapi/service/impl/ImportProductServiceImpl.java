package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.ImportProductDAO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.model.ImportProduct;
import com.laptech.restapi.service.ImportProductService;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class ImportProductServiceImpl implements ImportProductService {
    @Autowired
    private ImportProductDAO importProductDAO;

    @Autowired
    private ProductDAO productDAO;

    @Override
    public ImportProduct insert(ImportProduct importProduct) {
        // check

        if (importProductDAO.isExists(importProduct)) {
            throw new ResourceAlreadyExistsException("[Info] This ticket has already existed in system!");
        }

        String newTicketId = importProductDAO.insert(importProduct);
        if (newTicketId == null) {
            throw new InternalServerErrorException("[Error] Failed insert new ticket!");
        }
        return importProductDAO.findById(newTicketId);
    }

    @Override
    public void update(ImportProduct ticket, String ticketId) {
        // check

        ImportProduct oldTicket = importProductDAO.findById(ticketId);
        if (oldTicket == null) {
            throw new ResourceNotFoundException("[Info] Cannot find ticket with id=" + ticketId);
        } else {
            oldTicket.setProductId(ticket.getProductId());
            oldTicket.setQuantity(ticket.getQuantity());
            oldTicket.setImportedPrice(ticket.getImportedPrice());
            oldTicket.setImportedDate(ticket.getImportedDate());

            if (importProductDAO.update(oldTicket) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this ticket!");
            }
        }
    }

    @Override
    public void delete(String ticketId) {
        if (importProductDAO.findById(ticketId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find ticket with id=" + ticketId);
        } else {
            if (importProductDAO.delete(ticketId) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this ticket!");
        }
    }

    @Override
    public List<ImportProduct> findAll(Long page, Long size) {
        if (size == null)
            return importProductDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return importProductDAO.findAll(limit, skip);
    }

    @Override
    public ImportProduct findById(String ticketId) {
        ImportProduct ticket = importProductDAO.findById(ticketId);
        if (ticket == null) {
            throw new ResourceNotFoundException("[Info] Cannot find import product ticket with id=" + ticketId);
        }
        return ticket;
    }

    @Override
    public List<ImportProduct> findImportProductByProductId(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return importProductDAO.findImportProductTicketByProductId(productId);
    }

    @Override
    public Set<ImportProduct> filter(Map<String, String> params) {
        Set<ImportProduct> ticketSet = new HashSet<>(importProductDAO.findAll());
        Set<ImportProduct> notSuit = new HashSet<>();

        if (params.containsKey("productId")) {
            List<ImportProduct> ticketList = importProductDAO.findImportProductTicketByProductId(params.get("productId"));
            ticketList.forEach(ticket -> {
                if (!ticketSet.contains(ticket)) {
                    notSuit.add(ticket);
                }
            });
        }
        if (params.containsKey("date")) {
            List<ImportProduct> ticketList = importProductDAO.findImportProductTicketByDate(
                    LocalDate.parse(params.get("date"), DateTimeFormatter.ISO_LOCAL_DATE));
            ticketList.forEach(ticket -> {
                if (!ticketSet.contains(ticket)) {
                    notSuit.add(ticket);
                }
            });
        }
        if (params.containsKey("startDate") && params.containsKey("endedDate")) {
            List<ImportProduct> ticketList = importProductDAO.findImportProductTicketByDateRange(
                    ConvertDateTime.getDateTimeFromString(params.get("startDate")),
                    ConvertDateTime.getDateTimeFromString(params.get("endDate"))
            );
            ticketList.forEach(ticket -> {
                if (!ticketSet.contains(ticket)) {
                    notSuit.add(ticket);
                }
            });
        }
        ticketSet.removeAll(notSuit);

        return ticketSet;
    }
}
