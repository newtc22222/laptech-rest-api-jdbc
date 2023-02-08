package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.CommentDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Comment;
import com.laptech.restapi.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam(required = false, defaultValue = "1") Long page,
                                                        @RequestParam(required = false) Long size) {
        return ResponseEntity.ok(commentService.findAll(page, size));
    }

    @ApiOperation(value = "Get all comments of a product", response = Comment.class)
    @GetMapping("/products/{productId}/comments")
    public ResponseEntity<Collection<Comment>> getCommentsOfProduct(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(commentService.getAllCommentsOfProduct(productId));
    }

    @ApiOperation(value = "Get all comments of a user", response = Comment.class)
    @GetMapping("/users/{phoneNumber}/comments")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Collection<Comment>> getCommentsOfUser(@RequestParam("phoneNumber") String phone) {
        return ResponseEntity.ok(commentService.getAllCommentsOfUser(phone));
    }

    @ApiOperation(value = "Get one comment with id", response = Comment.class)
    @GetMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<Comment> findCommentById(@PathVariable("id") String commentId) {
        return ResponseEntity.ok(commentService.findById(commentId));
    }

    @ApiOperation(value = "Create a new comment", response = DataResponse.class)
    @PostMapping("/comments")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<?> createNewComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DataResponse(
                        HttpStatus.CREATED,
                        "Create new comment success!",
                        commentService.insert(CommentDTO.transform(commentDTO))
                ));
    }

    @ApiOperation(value = "Update a comment", response = BaseResponse.class)
    @PutMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> updateComment(@PathVariable("id") String commentId,
                                                      @RequestBody CommentDTO commentDTO) {
        commentService.update(CommentDTO.transform(commentDTO), commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse(
                        HttpStatus.OK,
                        "Update comment success!",
                        null
                ));
    }

    @ApiOperation(value = "Remove a comment", response = BaseResponse.class)
    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<BaseResponse> deleteComment(@PathVariable("id") String commentId) {
        commentService.delete(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse(
                        HttpStatus.OK,
                        "Delete comment success!",
                        null
                ));
    }
}
