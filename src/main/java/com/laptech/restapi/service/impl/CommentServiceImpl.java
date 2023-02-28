package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.InvalidArgumentException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.CommentDAO;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.model.Comment;
import com.laptech.restapi.service.CommentService;
import com.laptech.restapi.util.AuditUtil;
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
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private ProductDAO productDAO;

    @Override
    public Comment insert(Comment comment) {
        // check

        if (commentDAO.isExists(comment)) {
            throw new ResourceAlreadyExistsException("[Info] This comment has already existed in system!");
        }
        String newCommentId = commentDAO.insert(comment);
        if (newCommentId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new comment!");
        }
        return commentDAO.findById(newCommentId);
    }

    @Override
    public void update(Comment comment, String commentId) {
        // check

        Comment oldComment = commentDAO.findById(commentId);

        if (oldComment == null) {
            throw new ResourceNotFoundException("[Info] Cannot find comment with id=" + commentId);
        } else {
            oldComment.setPhone(comment.getPhone());
            oldComment.setUsername(comment.getUsername());
            oldComment.setContent(comment.getContent());

            if (commentDAO.update(oldComment) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this comment!");
            }
        }
    }

    @Override
    public void delete(String commentId, String updateBy) {
        if (commentDAO.findById(commentId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find comment with id=" + commentId);
        } else {
            if (commentDAO.delete(commentId, updateBy) == 0)
                throw new InternalServerErrorException("[Error] Failed to remove this comment!");
        }
    }

    @Override
    public long count() {
        return commentDAO.count();
    }

    @Override
    public Collection<Comment> findAll(String sortBy, String sortDir, Long page, Long size) {
        return commentDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<Comment> findWithFilter(Map<String, Object> params) {
        return null;
    }

    @Override
    public Comment findById(String commentId) {
        Comment comment = commentDAO.findById(commentId);
        if (comment == null) {
            throw new ResourceNotFoundException("[Info] Cannot find comment with id=" + commentId);
        }
        return comment;
    }

    @Override
    public Set<Comment> getAllCommentsOfProduct(String productId) {
        if (productDAO.findById(productId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        }
        return new HashSet<>(commentDAO.findCommentByProductId(productId));
    }

    @Override
    public Set<Comment> getAllCommentsOfUser(String phone) {
        String errorPhone = AuditUtil.getPhoneAudit(phone);
        if (!errorPhone.equals("")) {
            throw new InvalidArgumentException(errorPhone);
        }
        return new HashSet<>(commentDAO.findCommentByUserPhone(phone));
    }
}
