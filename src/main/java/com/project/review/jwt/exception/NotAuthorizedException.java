package com.project.review.jwt.exception;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class NotAuthorizedException extends BusinessException {

  public NotAuthorizedException() {
    super(ErrorCode.Not_Authorized_JWT);
  }
}
