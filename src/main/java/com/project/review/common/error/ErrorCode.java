package com.project.review.common.error;

public enum ErrorCode {

  // Auth Error
  INVALID_JWT(401, "A1", "유효하지 않은 토큰입니다"),
  Not_Authorized_JWT(403, "A2", "권한 정보가 없는 토큰입니다"),
  NOT_VALID_REFRESH_TOKEN(401,"A3","Refresh Token이 유효하지 않습니다"),
  LOG_OUT_USER(401,"A4", "로그아웃 된 사용자입니다"),
  TOKEN_USER_NOT_MATCH(401, "A5", "토큰의 유저 정보가 일치하지 않습니다"),
  NOT_AUTHENTICATE_SECURITY_CONTEXT(401, "A6","Security Context에 인증정보가 존재하지 않습니다"),

  // Common
  INVALID_INPUT_VALUE(400, "C1", "올바르지 않은 입력 값입니다"),
  INTERNAL_SERVER_ERROR(500, "C2", "서버 에러"),

  //User
  USER_NOT_FOUND(404, "U1", "해당 유저가 존재하지 않습니다"),
  DUPLICATED_USER(400, "U2", "이미 가입되어 있는 유저입니다"),

  //Restaurant
  RESTAURANT_NOT_FOUND(404, "R1", "해당 레스토랑이 존재하지 않습니다"),
  INVALID_RESTAURANT_RELATION(400, "R2", "잘못된 요청입니다");


  private final int status;
  private final String code;
  private final String message;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
