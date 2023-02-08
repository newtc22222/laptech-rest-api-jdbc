package com.laptech.restapi.service;

import com.laptech.restapi.model.Comment;

import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface CommentService extends BaseService<Comment, String> {
    Set<Comment> getAllCommentsOfProduct(String productId);

    Set<Comment> getAllCommentsOfUser(String phone);
}
