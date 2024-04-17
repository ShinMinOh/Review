package com.project.review.util.exception;

import static com.project.review.common.error.ErrorCode.NOT_AUTHENTICATE_SECURITY_CONTEXT;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class NoAuthenticationInfoException extends BusinessException {

  public NoAuthenticationInfoException() {
    super(NOT_AUTHENTICATE_SECURITY_CONTEXT);
  }
}
