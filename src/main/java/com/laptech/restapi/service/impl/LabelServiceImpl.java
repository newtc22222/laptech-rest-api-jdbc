package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.LabelDAO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dto.filter.LabelFilter;
import com.laptech.restapi.model.Label;
import com.laptech.restapi.service.LabelService;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @since 2023-02-06
 */
@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelDAO labelDAO;

    @Autowired
    private ProductDAO productDAO;

    @Override
    public Label insert(Label label) {
        // check

        if (labelDAO.isExists(label)) {
            throw new ResourceAlreadyExistsException("[Info] This label has already existed in system!");
        }

        Long newLabelId = labelDAO.insert(label);
        if (newLabelId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new label!");
        }
        return labelDAO.findById(newLabelId);
    }

    @Override
    public void update(Label label, Long labelId) {
        // check

        Label oldLabel = labelDAO.findById(labelId);
        if (oldLabel == null) {
            throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
        } else {
            oldLabel.setName(label.getName());
            oldLabel.setIcon(label.getIcon());
            oldLabel.setTitle(label.getTitle());
            oldLabel.setDescription(label.getDescription());
            oldLabel.setUpdateBy(label.getUpdateBy());

            if (labelDAO.update(oldLabel) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update label!");
            }
        }
    }

    @Override
    public void delete(Long labelId, String updateBy) {
        if (labelDAO.findById(labelId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
        } else {
            if (labelDAO.delete(labelId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this label!");
            }
        }
    }

    @Override
    public long count() {
        return labelDAO.count();
    }

    @Override
    public Collection<Label> findAll(String sortBy, String sortDir, Long page, Long size) {
        return labelDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Label> findWithFilter(Map<String, Object> params) {
        LabelFilter filter = new LabelFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return labelDAO.findWithFilter(filter);
    }

    @Override
    public Label findById(Long labelId) {
        Label label = labelDAO.findById(labelId);
        if (label == null) {
            throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
        }
        return label;
    }

    @Override
    public Collection<Label> getLabelsOfProduct(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return labelDAO.findLabelByProductId(productId);
    }
}
