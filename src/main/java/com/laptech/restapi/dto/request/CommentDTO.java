package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Comment;
import com.laptech.restapi.util.ConvertDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
@ApiModel("Class representing a comment request body")
public class CommentDTO {
    private String rootCommentId;
    private String createdDate;
    @ApiModelProperty(required = true)
    private String productId;
    @ApiModelProperty(required = true)
    private String username;
    @ApiModelProperty(required = true)
    private String phone;
    @ApiModelProperty(required = true)
    private String content;

    public static Comment transform(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        if (commentDTO.getRootCommentId() != null) {
            comment.setRootCommentId(commentDTO.getRootCommentId());
        }
        comment.setProductId(commentDTO.getProductId());
        comment.setUsername(commentDTO.getUsername());
        comment.setPhone(commentDTO.getPhone());
        comment.setContent(commentDTO.getContent());

        if (commentDTO.getCreatedDate() != null) {
            comment.setCreatedDate(ConvertDateTime.getDateTimeFromString(commentDTO.getCreatedDate()));
        }
        return comment;
    }
}

