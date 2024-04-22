package com.project.review.service.exception;

import static com.project.review.common.error.ErrorCode.INVALID_REVIEW_RELATION;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class InvalidReviewException extends BusinessException {

  public InvalidReviewException() {
    super(INVALID_REVIEW_RELATION);
  }
}
