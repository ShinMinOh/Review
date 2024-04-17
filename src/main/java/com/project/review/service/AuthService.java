package com.project.review.service;

import com.project.review.dto.MemberRequestDto;
import com.project.review.dto.MemberResponseDto;
import com.project.review.dto.TokenDto;
import com.project.review.dto.TokenRequestDto;
import com.project.review.jwt.TokenProvider;
import com.project.review.model.Member;
import com.project.review.model.RefreshToken;
import com.project.review.repository.MemberRepository;
import com.project.review.repository.RefreshTokenRepository;
import com.project.review.service.exception.DuplicatedUserException;
import com.project.review.service.exception.LogoutUserException;
import com.project.review.service.exception.RefreshTokenNotValidException;
import com.project.review.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  /**
   *  회원 가입
   * */
  @Transactional
  public MemberResponseDto signup(MemberRequestDto memberRequestDto){
    if(memberRepository.existsByEmail(memberRequestDto.email())){
      throw new DuplicatedUserException();
    }
    Member member = memberRequestDto.toMember(passwordEncoder);

    Member saveMember = memberRepository.save(member);

    return MemberResponseDto.of(saveMember);
  }

  @Transactional
  public TokenDto login(MemberRequestDto memberRequestDto){
    // 1. Login할때 입력한 ID/PW로 AuthenticationToken 생성
    UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

    // 2. 실제로 검증(사용자 비밀번호 체크)
    // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 3. 인증 정보 기반으로 Jwt 토큰 생성
    TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

    // 4. RefreshToken 저장
    RefreshToken refreshToken = RefreshToken.builder()
        .key(authentication.getName())
        .value(tokenDto.refreshToken())
        .build();

    refreshTokenRepository.save(refreshToken);

    // 5. 토큰 발급
    return tokenDto;
  }

  @Transactional
  public TokenDto reissue(TokenRequestDto tokenRequestDto){
    // 1. Refresh Token 검증
    if(!tokenProvider.validateToken(tokenRequestDto.refreshToken())){
      throw new RefreshTokenNotValidException();
    }

    // 2. Access Token에서 Member ID 가져 오기
    Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.accessToken());

    // 3. 레포지토리에서 Member Id로 RefreshToken 값 가져옴
    RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
        .orElseThrow(LogoutUserException::new);

    // 4. Refresh Token 일치하는지 검사
    if(!refreshToken.getValue().equals(tokenRequestDto.refreshToken())){
      throw new UserNotFoundException();
    }

    // 5. 새로운 토큰 생성
    TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

    // 6. 새로운 RefreshToken 업데이트
    RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.refreshToken());
    refreshTokenRepository.save(newRefreshToken);

    // 7. 토큰 발급
    return tokenDto;
  }

}
