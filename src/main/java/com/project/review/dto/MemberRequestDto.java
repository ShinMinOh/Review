package com.project.review.dto;

import com.project.review.model.Authority;
import com.project.review.model.Member;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

// 회원가입시 입력값 받는 DTO
public record MemberRequestDto(
    @NotBlank String email,
    @NotBlank String password
    ) {
  public Member toMember(PasswordEncoder passwordEncoder){
    return Member.builder()
        .email(email)
        .password(passwordEncoder.encode(password))
        .authority(Authority.ROLE_USER)
        .build();
  }

  public UsernamePasswordAuthenticationToken toAuthentication(){
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}
