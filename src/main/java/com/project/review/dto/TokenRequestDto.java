package com.project.review.dto;

// Access Token이 만료된 경우, 재발급 위해 만료된 accessToken과 refreshToken을 보냄.
public record TokenRequestDto(
    String accessToken,
    String refreshToken
) {

}
