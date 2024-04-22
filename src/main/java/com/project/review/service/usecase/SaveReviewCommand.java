package com.project.review.service.usecase;

import com.project.review.model.Member;
import com.project.review.model.Review;

public record SaveReviewCommand(
    String content,
    Double score,
    Long memberId,
    Long restaurantId
) {


}
