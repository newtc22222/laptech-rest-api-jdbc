package com.laptech.restapi.dao;

import com.laptech.restapi.model.Comment;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface CommentDAO extends BaseDAO<Comment, String> {
    List<Comment> findCommentByProductId(String productId);

    /**
     * Comment will be gotten by user phone number
     */
    List<Comment> findCommentByUserPhone(String phone);
}
