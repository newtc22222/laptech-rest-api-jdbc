package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.CommentFilter;
import com.laptech.restapi.model.Comment;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface CommentDAO extends BaseDAO<Comment, CommentFilter, String> {
    Collection<Comment> findCommentByProductId(String productId);

    /**
     * Comment will be gotten by user phone number
     */
    Collection<Comment> findCommentByUserPhone(String phone);
}
