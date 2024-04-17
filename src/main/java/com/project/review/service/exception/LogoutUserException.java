package com.project.review.service.exception;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class LogoutUserException extends BusinessException {

  public LogoutUserException() {
    super(ErrorCode.LOG_OUT_USER);
  }
}
