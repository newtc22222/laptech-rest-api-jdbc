package com.laptech.restapi.controller;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Feedback;
import com.laptech.restapi.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Feedback of Product", value = "Feedback controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation(value = "Get all feedbacks (with limit)", response = Feedback.class)
    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getNewFeedbacks(@RequestParam(required = false, defaultValue = "1") Long page,
                                                          @RequestParam(required = false) Long size) {
        return ResponseEntity.ok(feedbackService.findAll(page, size));
    }

    @ApiOperation(value = "Get one feedback with id", response = Feedback.class)
    @GetMapping("/feedbacks/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") String feedbackId) {
        return ResponseEntity.ok(feedbackService.findById(feedbackId));
    }

    @ApiOperation(value = "Get all feedbacks of a product", response = Feedback.class)
    @GetMapping("/products/{productId}/feedbacks")
    public ResponseEntity<Collection<Feedback>> getFeedbacksOfProduct(@PathVariable("productId") String productId,
                                                                      @RequestParam(value = "point", required = false) Byte ratingPoint) {
        if (ratingPoint != null) {
            return ResponseEntity.ok(feedbackService.getFeedbacksOfProductByRatingPoint(productId, ratingPoint));
        }
        return ResponseEntity.ok(feedbackService.getAllFeedbacksOfProduct(productId));
    }

    @ApiOperation(value = "Get all feedbacks of user with userId", response = Feedback.class)
    @GetMapping("/users/{userId}/feedbacks")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Collection<Feedback>> getFeedbacksOfUser(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(feedbackService.getAllFeedbacksOfUser(userId));
    }

    @ApiOperation(value = "Create a new feedback", response = DataResponse.class)
    @PostMapping("/feedbacks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> createNewFeedback(@RequestBody Feedback feedback) {
        return DataResponse.success(
                "Create new feedback",
                feedbackService.insert(feedback)
        );
    }

    @ApiOperation(value = "Update a feedback", response = BaseResponse.class)
    @PutMapping("/feedbacks/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateFeedback(@PathVariable("id") String feedbackId,
                                                       @RequestBody Feedback feedback) {
        feedbackService.update(feedback, feedbackId);
        return DataResponse.success("Update feedback");
    }

    @ApiOperation(value = "Delete a feedback", response = BaseResponse.class)
    @DeleteMapping("/feedbacks/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> deleteFeedback(@PathVariable("id") String feedbackId) {
        feedbackService.delete(feedbackId);
        return DataResponse.success("Delete feedback");
    }
}
