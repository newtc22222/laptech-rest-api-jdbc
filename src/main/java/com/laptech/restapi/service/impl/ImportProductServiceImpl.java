package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.ImportProductDAO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dto.filter.ImportProductFilter;
import com.laptech.restapi.model.ImportProduct;
import com.laptech.restapi.service.ImportProductService;
import com.laptech.restapi.util.ConvertDate;
import com.laptech.restapi.util.ConvertDateTime;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
            oldTicket.setUpdateBy(ticket.getUpdateBy());

            if (importProductDAO.update(oldTicket) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this ticket!");
            }
        }
    }

    @Override
    public void delete(String ticketId, String updateBy) {
        if (importProductDAO.findById(ticketId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find ticket with id=" + ticketId);
        } else {
            if (importProductDAO.delete(ticketId, updateBy) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this ticket!");
        }
    }

    @Override
    public long count() {
        return importProductDAO.count();
    }

    @Override
    public Collection<ImportProduct> findAll(String sortBy, String sortDir, Long page, Long size) {
        return importProductDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<ImportProduct> findWithFilter(Map<String, Object> params) {
        ImportProductFilter filter = new ImportProductFilter(ConvertMap.changeAllValueFromObjectToString(params));
        Set<ImportProduct> ticketSet = (Set<ImportProduct>) importProductDAO.findWithFilter(filter);

        if (params.containsKey("productId")) {
            Collection<ImportProduct> ticketList = importProductDAO.findImportProductByProductId(params.get("productId").toString());
            ticketSet.removeIf(item -> !ticketList.contains(item));
        }
        if (params.containsKey("date")) {
            Collection<ImportProduct> ticketList = importProductDAO.findImportProductByDate(
                    ConvertDate.getDateFromString(params.get("date").toString()));
            ticketSet.removeIf(item -> !ticketList.contains(item));
        }
        if (params.containsKey("startDate") && params.containsKey("endedDate")) {
            Collection<ImportProduct> ticketList = importProductDAO.findImportProductByDateRange(
                    ConvertDateTime.getDateTimeFromString(params.get("startDate").toString()),
                    ConvertDateTime.getDateTimeFromString(params.get("endDate").toString())
            );
            ticketSet.removeIf(item -> !ticketList.contains(item));
        }
        return ticketSet;
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
    public Collection<ImportProduct> findImportProductByProductId(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return importProductDAO.findImportProductByProductId(productId);
    }
}
