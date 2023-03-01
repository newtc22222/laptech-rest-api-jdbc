package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public CommentFilter(Map<String, String> params) {
        super(params);
        this.rootCommentId = params.get("rootCommentId");
        this.productId = params.get("productId");
        this.username = params.get("username");
        this.phone = params.get("phone");
        this.content = params.get("content");
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.rootCommentId);
        objects.add(this.productId);
        objects.add(this.username);
        objects.add(this.phone);
        objects.add(this.content);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
