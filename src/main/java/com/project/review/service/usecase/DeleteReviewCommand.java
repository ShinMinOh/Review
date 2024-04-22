package com.project.review.service.usecase;

public record DeleteReviewCommand(
    Long memberId,
    Long restaurantId,
    Long reviewId
) {

}
