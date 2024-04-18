package com.project.review.service;

import com.project.review.dto.MemberResponseDto;
import com.project.review.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberResponseDto findMemberInfoById(Long memberId) {
    return memberRepository.findById(memberId)
        .map(MemberResponseDto::of)
        .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
  }

  public MemberResponseDto findMemberInfoByEmail(String email) {
    return memberRepository.findByEmail(email)
        .map(MemberResponseDto::of)
        .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
  }
}
