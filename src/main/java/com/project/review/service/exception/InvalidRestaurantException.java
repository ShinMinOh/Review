package com.project.review.service.exception;

import static com.project.review.common.error.ErrorCode.INVALID_RESTAURANT_RELATION;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class InvalidRestaurantException extends BusinessException {

  public InvalidRestaurantException() {
    super(INVALID_RESTAURANT_RELATION);
  }
}
