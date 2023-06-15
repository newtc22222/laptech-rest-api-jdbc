package com.laptech.restapi.controller;

import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.dto.request.FeedbackDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Feedback;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.FeedbackService;
import com.laptech.restapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;

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
    public ResponseEntity<DataResponse> getFeedbackWithFilter(@RequestParam(required = false) String productId,
                                                              @RequestParam(required = false) Long userId,
                                                              @RequestParam(required = false) String content,
                                                              @RequestParam(required = false) Byte ratingPoint,
                                                              @RequestParam(required = false) String createdDate,
                                                              @RequestParam(required = false) String modifiedDate,
                                                              @RequestParam(required = false) String deletedDate,
                                                              @RequestParam(required = false) Boolean isDel,
                                                              @RequestParam(required = false) String updateBy,
                                                              @RequestParam(required = false) String sortBy,
                                                              @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("userId", userId);
        params.put("content", content);
        params.put("ratingPoint", ratingPoint);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);
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
    public ResponseEntity<DataResponse> createNewFeedback(HttpServletRequest request, @Valid @RequestBody FeedbackDTO dto) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findUserByPhone(principal.getName());
        if (user.getId() != dto.getUserId()) {
            dto.setUserId(user.getId()); // trick remove feedback has wrong user id
        }

        Collection<Feedback> oldFeedbackOfUser = feedbackService.getAllFeedbacksOfUser(dto.getUserId());
        Collection<Feedback> listFeedbackDuplicate = oldFeedbackOfUser
                .stream()
                .filter(feedback -> feedback.getProductId().equals(dto.getProductId()))
                .collect(Collectors.toList());
        if (listFeedbackDuplicate.size() > 0) {
            Feedback duplicateFeedback = (Feedback) listFeedbackDuplicate.toArray()[0];
            throw new ResourceAlreadyExistsException(
                    "This user has created another feedback in " + duplicateFeedback.getCreatedDate()
            );
        }
        return DataResponse.success(
                "Create new feedback",
                feedbackService.insert(FeedbackDTO.transform(dto))
        );
    }

    @ApiOperation(value = "Update a feedback", response = BaseResponse.class)
    @PutMapping("/feedbacks/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateFeedback(HttpServletRequest request,
                                                       @PathVariable("id") String feedbackId,
                                                       @Valid @RequestBody FeedbackDTO dto) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findUserByPhone(principal.getName());
        if (user.getId() != dto.getUserId()) {
            dto.setUserId(user.getId()); // trick remove feedback has wrong user id
        }
        feedbackService.update(FeedbackDTO.transform(dto), feedbackId);
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
