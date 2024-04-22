package com.project.review.controller;

import com.project.review.controller.request.SaveReviewRequestDto;
import com.project.review.model.Review;
import com.project.review.service.ReviewService;
import com.project.review.service.usecase.DeleteReviewCommand;
import jakarta.validation.constraints.Min;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@Validated
public class ReviewController {

  private final ReviewService reviewService;

  // 리뷰 작성 API
  @PostMapping("/restaurant/{restaurantId}/review")
  public ResponseEntity<Object> createReview(
      @PathVariable @Min(1) Long restaurantId,
      @RequestBody SaveReviewRequestDto saveReviewRequestDto,
      @AuthenticationPrincipal UserDetails userDetails
  ){
    long userId = Long.parseLong(userDetails.getUsername());

    Review savedReview = reviewService.createReview(saveReviewRequestDto.toCommand(userId,restaurantId));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedReview.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }
    
  // 리뷰 삭제 API
  @DeleteMapping("/restaurant/{restaurantId}/review/{reviewId}")
  public ResponseEntity<Object> deleteReview(
      @PathVariable @Min(1) Long restaurantId,
      @PathVariable @Min(1) Long reviewId,
      @AuthenticationPrincipal UserDetails userDetails
      ){
    long userId = Long.parseLong(userDetails.getUsername());
    reviewService.deleteReview(new DeleteReviewCommand(userId, restaurantId, reviewId));

    return ResponseEntity.noContent().build();
  }

  // 해당 맛집에 등록된 리뷰 페이징 조회 API
  @GetMapping("/restaurant/{restaurantId}/reviews")
  public ResponseEntity<Object> getReviews(
      @PathVariable @Min(1) Long restaurantId,

  ){

  }
}
