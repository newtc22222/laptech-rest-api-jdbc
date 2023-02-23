package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nhat Phi
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseModel {
    private String id;
    private String rootCommentId;
    private String productId;
    private String username;
    private String phone;
    private String content;

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", rootCommentId='" + rootCommentId + '\'' +
                ", productId='" + productId + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                "} " + super.toString();
    }
}
