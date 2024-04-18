package com.project.review.service.exception;

import static com.project.review.common.error.ErrorCode.TOKEN_USER_NOT_MATCH;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class NotMatchTokenUserException extends BusinessException {

  public NotMatchTokenUserException() {
    super(TOKEN_USER_NOT_MATCH);
  }
}
