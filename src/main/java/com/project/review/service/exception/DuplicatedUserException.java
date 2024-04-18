package com.project.review.service.exception;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class DuplicatedUserException extends BusinessException {

  public DuplicatedUserException() {
    super(ErrorCode.DUPLICATED_USER);
  }
}
