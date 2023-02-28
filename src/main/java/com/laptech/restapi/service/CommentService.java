package com.laptech.restapi.service;

import com.laptech.restapi.model.Comment;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface CommentService extends BaseService<Comment, String> {
    Collection<Comment> getAllCommentsOfProduct(String productId);

    Collection<Comment> getAllCommentsOfUser(String phone);
}
