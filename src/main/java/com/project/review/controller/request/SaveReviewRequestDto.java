package com.project.review.controller.request;

import com.project.review.service.usecase.SaveReviewCommand;

public record SaveReviewRequestDto(
    String content,
    Double score

) {

  public SaveReviewCommand toCommand(Long userId, Long restaurantId){
    return new SaveReviewCommand(content, score, userId, restaurantId);
  }
}
