package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.FeedbackDAO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.dto.filter.FeedbackFilter;
import com.laptech.restapi.model.Feedback;
import com.laptech.restapi.service.FeedbackService;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDAO feedbackDAO;

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public Feedback insert(Feedback feedback) {
        // check

        if (feedbackDAO.isExists(feedback)) {
            throw new ResourceAlreadyExistsException("[Info] This feedback has already existed in system!");
        }

        String newFeedbackId = feedbackDAO.insert(feedback);
        if (newFeedbackId == null) {
            throw new InternalServerErrorException("[Error] Failed insert new feedback!");
        }
        return feedbackDAO.findById(newFeedbackId);
    }

    @Override
    public void update(Feedback feedback, String feedbackId) {
        // check

        Feedback oldFeedback = feedbackDAO.findById(feedbackId);
        if (oldFeedback == null) {
            throw new ResourceNotFoundException("[Info] Cannot find feedback with id=" + feedbackId);
        } else {
            oldFeedback.setProductId(feedback.getProductId());
            oldFeedback.setUserId(feedback.getUserId());
            oldFeedback.setContent(feedback.getContent());
            oldFeedback.setRatingPoint(feedback.getRatingPoint());
            oldFeedback.setUpdateBy(feedback.getUpdateBy());

            if (feedbackDAO.update(oldFeedback) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this feedback!");
            }
        }
    }

    @Override
    public void delete(String feedbackId, String updateBy) {
        if (feedbackDAO.findById(feedbackId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find feedback with id=" + feedbackId);
        } else {
            if (feedbackDAO.delete(feedbackId, updateBy) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this feedback!");
        }
    }

    @Override
    public long count() {
        return feedbackDAO.count();
    }

    @Override
    public Collection<Feedback> findAll(String sortBy, String sortDir, Long page, Long size) {
        return feedbackDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Feedback> findWithFilter(Map<String, Object> params) {
        FeedbackFilter filter = new FeedbackFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return feedbackDAO.findWithFilter(filter);
    }

    @Override
    public Feedback findById(String feedbackId) {
        Feedback feedback = feedbackDAO.findById(feedbackId);
        if (feedback == null) {
            throw new ResourceNotFoundException("[Info] Cannot find feedback with id=" + feedbackId);
        }
        return feedback;
    }

    @Override
    public Set<Feedback> getAllFeedbacksOfProduct(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return new HashSet<>(feedbackDAO.findFeedbackByProductId(productId));
    }

    @Override
    public Set<Feedback> getAllFeedbacksOfUser(long userId) {
        if (userDAO.findById(userId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find user with id=" + userId);
        }
        return new HashSet<>(feedbackDAO.findFeedbackByUserId(userId));
    }
}
