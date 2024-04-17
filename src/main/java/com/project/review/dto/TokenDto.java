package com.project.review.dto;

//회원가입한 인증정보로 로그인 할 경우, 주어지는 인증 정보 토큰
public record TokenDto(
    String grantType,
    String accessToken,
    String refreshToken,
    Long accessTokenExpiresIn
) {
}
