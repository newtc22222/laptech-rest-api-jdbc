package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
@ApiModel("Class representing a comment request body")
public class CommentDTO {
    private String id;
    private String rootCommentId;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String productId;
    @ApiModelProperty(required = true)
    @Size(min = 3, max = 100)
    private String username;
    @ApiModelProperty(required = true)
    @NotNull
    @Size(min = 13, max = 15)
    private String phone;
    @ApiModelProperty(required = true)
    @Size(min = 10, max = 255)
    private String content;
    @Size(max = 100)
    private String updateBy;

    public CommentDTO() {
    }

    public CommentDTO(String id, String rootCommentId, String productId, String username, String phone, String content, String updateBy) {
        this.id = id;
        this.rootCommentId = rootCommentId;
        this.productId = productId;
        this.username = username;
        this.phone = phone;
        this.content = content;
        this.updateBy = updateBy;
    }

    public static Comment transform(CommentDTO dto) {
        String newCommentId = dto.getId() != null
                ? dto.getId()
                : UUID.randomUUID().toString().replace("-", "").substring(0, 15);

        Comment comment = new Comment();
        comment.setId(newCommentId);
        comment.setRootCommentId(dto.getRootCommentId());
        comment.setProductId(dto.getProductId());
        comment.setUsername(dto.getUsername());
        comment.setPhone(dto.getPhone());
        comment.setContent(dto.getContent());
        comment.setUpdateBy(dto.getUpdateBy());
        return comment;
    }
}

