package com.project.review.dto;

import com.project.review.model.Member;

// 회원가입 성공시 이메일 반환 하는 DTO
public record MemberResponseDto(
    String email
) {
  public static MemberResponseDto of(Member member){
    return new MemberResponseDto(member.getEmail());
  }
}
