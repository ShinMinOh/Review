package com.project.review.service.exception;

import static com.project.review.common.error.ErrorCode.USER_NOT_FOUND;

import com.project.review.common.error.ErrorCode;
import com.project.review.common.error.exception.BusinessException;

public class UserNotFoundException extends BusinessException {


  public UserNotFoundException(Long memberId) {
    super("Member ID=" + memberId, USER_NOT_FOUND);
  }
}
