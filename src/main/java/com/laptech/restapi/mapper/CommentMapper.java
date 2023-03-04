package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Comment;
import com.laptech.restapi.util.ConvertBaseModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getString("id"));
        comment.setRootCommentId(rs.getString("root_comment_id")); // maybe null
        comment.setProductId(rs.getString("product_id"));
        comment.setUsername(rs.getNString("username"));
        comment.setPhone(rs.getString("phone"));
        comment.setContent(rs.getNString("content"));
        comment.setData(ConvertBaseModel.getBaseModel(rs));
        return comment;
    }
}
