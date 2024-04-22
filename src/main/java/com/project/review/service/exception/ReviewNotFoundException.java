package com.project.review.service.exception;

import static com.project.review.common.error.ErrorCode.RESTAURANT_NOT_FOUND;
import static com.project.review.common.error.ErrorCode.REVIEW_NOT_FOUND;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class ReviewNotFoundException extends BusinessException {

  public ReviewNotFoundException(Long reviewId) {
    super("Review ID: "+reviewId,REVIEW_NOT_FOUND);
  }
}
