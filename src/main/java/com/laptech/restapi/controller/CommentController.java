package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.CommentDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Comment;
import com.laptech.restapi.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Comment of product", value = "Comment controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Get all comments", response = Comment.class)
    @GetMapping("/comments")
    public ResponseEntity<DataResponse> getAllComments(@RequestParam(required = false) String sortBy,
                                                       @RequestParam(required = false) String sortDir,
                                                       @RequestParam(required = false) Long page,
                                                       @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all comments",
                commentService.count(),
                commentService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get comments with filter", response = Comment.class)
    @GetMapping("/comments/filter")
    public ResponseEntity<DataResponse> getCommentWithFilter(@RequestParam(required = false) String rootCommentId,
                                                             @RequestParam(required = false) String productId,
                                                             @RequestParam(required = false) String username,
                                                             @RequestParam(required = false) String phone,
                                                             @RequestParam(required = false) String content, // can upgrade
                                                             @RequestParam(required = false) String createdDate,
                                                             @RequestParam(required = false) String modifiedDate,
                                                             @RequestParam(required = false) String deletedDate,
                                                             @RequestParam(required = false) Boolean isDel,
                                                             @RequestParam(required = false) String updateBy,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("rootCommentId", rootCommentId);
        params.put("productId", productId);
        params.put("username", username);
        params.put("phone", phone);
        params.put("content", content);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);
        return DataResponse.getCollectionSuccess(
                "Get all comments",
                commentService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get all comments of a product", response = Comment.class)
    @GetMapping("/products/{productId}/comments")
    public ResponseEntity<DataResponse> getCommentsOfProduct(@PathVariable("productId") String productId) {
        return DataResponse.getCollectionSuccess(
                "Get comments of product",
                commentService.getAllCommentsOfProduct(productId)
        );
    }

    @ApiOperation(value = "Get all comments of a user", response = Comment.class)
    @GetMapping("/users/{phone}/comments")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getCommentsOfUser(@PathVariable String phone) {
        return DataResponse.getCollectionSuccess(
                "Get comments of user",
                commentService.getAllCommentsOfUser(phone)
        );
    }

    @ApiOperation(value = "Get one comment with id", response = Comment.class)
    @GetMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<DataResponse> findCommentById(@PathVariable("id") String commentId) {
        return DataResponse.getObjectSuccess(
                "Get comment",
                commentService.findById(commentId)
        );
    }

    @ApiOperation(value = "Create a new comment", response = DataResponse.class)
    @PostMapping("/comments")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<DataResponse> createNewComment(@RequestBody CommentDTO commentDTO) {
        return DataResponse.success(
                "Create new comment",
                commentService.insert(CommentDTO.transform(commentDTO))
        );
    }

    @ApiOperation(value = "Update a comment", response = BaseResponse.class)
    @PutMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateComment(@PathVariable("id") String commentId,
                                                      @RequestBody CommentDTO commentDTO) {
        commentService.update(CommentDTO.transform(commentDTO), commentId);
        return DataResponse.success("Update comment");
    }

    @ApiOperation(value = "Remove a comment", response = BaseResponse.class)
    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> deleteComment(@PathVariable("id") String commentId,
                                                      @RequestBody(required = false) Map<String, String> body) {
        commentService.delete(commentId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete comment");
    }
}
