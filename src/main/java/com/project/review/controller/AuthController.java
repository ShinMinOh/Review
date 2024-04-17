package com.project.review.controller;

import com.project.review.dto.MemberRequestDto;
import com.project.review.dto.MemberResponseDto;
import com.project.review.dto.TokenDto;
import com.project.review.dto.TokenRequestDto;
import com.project.review.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/auth/signup")
  public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto){
    return ResponseEntity.ok(authService.signup(memberRequestDto));
  }

  @PostMapping("/auth/login")
  public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto){
    return ResponseEntity.ok(authService.login(memberRequestDto));
  }

  @PostMapping("/auth/reissue")
  public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
    return ResponseEntity.ok(authService.reissue(tokenRequestDto));
  }
}
