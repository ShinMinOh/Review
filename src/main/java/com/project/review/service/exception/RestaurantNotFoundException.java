package com.project.review.service.exception;

import static com.project.review.common.error.ErrorCode.RESTAURANT_NOT_FOUND;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class RestaurantNotFoundException extends BusinessException {

  public RestaurantNotFoundException() {
    super(RESTAURANT_NOT_FOUND);
  }
}
