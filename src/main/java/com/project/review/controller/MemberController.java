package com.project.review.controller;

import com.project.review.dto.MemberResponseDto;
import com.project.review.service.MemberService;
import com.project.review.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/member/me")
  public ResponseEntity<MemberResponseDto> findMemberInfoById() {
    return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
  }

  @GetMapping("/member/{email}")
  public ResponseEntity<MemberResponseDto> findMemberInfoByEmail(@PathVariable String email) {
    return ResponseEntity.ok(memberService.findMemberInfoByEmail(email));
  }
}
