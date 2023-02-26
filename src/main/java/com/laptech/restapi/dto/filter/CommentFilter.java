package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter @Setter
public class CommentFilter extends BaseFilter {
    private String rootCommentId;
    private String productId;
    private String username;
    private String phone;
    private String content;

    public CommentFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                         LocalDate deletedDate, Boolean isDel, String updateBy, String rootCommentId, String productId,
                         String username, String phone, String content) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.rootCommentId = rootCommentId;
        this.productId = productId;
        this.username = username;
        this.phone = phone;
        this.content = content;
    }
}
