package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.LabelDAO;
import com.laptech.restapi.model.Label;
import com.laptech.restapi.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @since 2023-02-06
 */
@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelDAO labelDAO;

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

            if (labelDAO.update(oldLabel) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update label!");
            }
        }
    }

    @Override
    public void delete(Long labelId) {
        if (labelDAO.findById(labelId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
        } else {
            if (labelDAO.delete(labelId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this label!");
            }
        }
    }

    @Override
    public List<Label> findAll(Long page, Long size) {
        if (size == null)
            return labelDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return labelDAO.findAll(limit, skip);
    }

    @Override
    public Label findById(Long labelId) {
        Label label = labelDAO.findById(labelId);
        if (label == null) {
            throw new ResourceNotFoundException("[Info] Cannot find label with id=" + labelId);
        }
        return label;
    }
}
