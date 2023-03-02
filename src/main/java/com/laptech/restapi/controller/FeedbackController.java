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

import java.util.HashMap;
import java.util.Map;

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

    @ApiOperation(value = "Get all feedbacks", response = Feedback.class)
    @GetMapping("/feedbacks")
    public ResponseEntity<DataResponse> getAllFeedbacks(@RequestParam(required = false) String sortBy,
                                                        @RequestParam(required = false) String sortDir,
                                                        @RequestParam(required = false) Long page,
                                                        @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all feedbacks",
                feedbackService.count(),
                feedbackService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get feedback with filter", response = Feedback.class)
    @GetMapping("/feedbacks/filter")
    public ResponseEntity<DataResponse> getFeedbackWithFilter() {
        Map<String, Object> params = new HashMap<>();

        return DataResponse.getCollectionSuccess(
                "Get feedback with filter",
                feedbackService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get one feedback with id", response = Feedback.class)
    @GetMapping("/feedbacks/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<DataResponse> getFeedbackById(@PathVariable("id") String feedbackId) {
        return DataResponse.getObjectSuccess(
                "Get feedback",
                feedbackService.findById(feedbackId)
        );
    }

    @ApiOperation(value = "Get all feedbacks of a product", response = Feedback.class)
    @GetMapping("/products/{productId}/feedbacks")
    public ResponseEntity<DataResponse> getFeedbacksOfProduct(@PathVariable("productId") String productId) {
        return DataResponse.getCollectionSuccess(
                "Get feedback of product",
                feedbackService.getAllFeedbacksOfProduct(productId)
        );
    }

    @ApiOperation(value = "Get all feedbacks of user with userId", response = Feedback.class)
    @GetMapping("/users/{userId}/feedbacks")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getFeedbacksOfUser(@PathVariable("userId") long userId) {
        return DataResponse.getCollectionSuccess(
                "Get feedback of user",
                feedbackService.getAllFeedbacksOfUser(userId)
        );
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
    public ResponseEntity<BaseResponse> deleteFeedback(@PathVariable("id") String feedbackId,
                                                       @RequestBody(required = false) Map<String, String> body) {
        feedbackService.delete(feedbackId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete feedback");
    }
}
