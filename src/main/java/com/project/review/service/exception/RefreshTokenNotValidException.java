package com.project.review.service.exception;

import static com.project.review.common.error.ErrorCode.NOT_VALID_REFRESH_TOKEN;

import com.project.review.common.error.exception.BusinessException;

public class RefreshTokenNotValidException extends BusinessException {

  public RefreshTokenNotValidException() {
    super(NOT_VALID_REFRESH_TOKEN);
  }
}
