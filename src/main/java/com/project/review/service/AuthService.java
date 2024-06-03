package com.project.review.service;

import com.project.review.dto.MemberRequestDto;
import com.project.review.dto.MemberResponseDto;
import com.project.review.dto.TokenDto;
import com.project.review.dto.TokenRequestDto;
import com.project.review.jwt.TokenProvider;
import com.project.review.model.Member;
import com.project.review.repository.MemberRepository;
import com.project.review.service.exception.DuplicatedUserException;
import com.project.review.service.exception.LogoutUserException;
import com.project.review.service.exception.NotMatchTokenUserException;
import com.project.review.service.exception.RefreshTokenNotValidException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final RedisTemplate<String, String> redisTemplate;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

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

    // 4. Redis 캐시에 RefreshToken 저장
    saveRefreshTokenCache(authentication, tokenDto);

    // 5. 토큰 발급
    return tokenDto;
  }

  private void saveRefreshTokenCache(Authentication authentication, TokenDto tokenDto) {
    final long REDIS_EXPIRE_TIME = 1000 * 60 * 60 * 2;  // 2시간.

    redisTemplate.opsForValue().set(
        authentication.getName(),             // 키: 사용자 이름
        tokenDto.refreshToken(),              // 값: refresh 토큰
        REDIS_EXPIRE_TIME,                    // 유효 기간이 지나면 Redis에서 자동 삭제
        TimeUnit.MILLISECONDS                 // ms
    );
  }

  @Transactional
  public TokenDto reissue(TokenRequestDto tokenRequestDto){
    // 1. 유효한 Refresh Token 검증
    if(!tokenProvider.validateToken(tokenRequestDto.refreshToken())){
      throw new RefreshTokenNotValidException();
    }

    // 2. Access Token에서 Authentication 객체 가져 오기
    Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.accessToken());

    // 3. Redis에서 Member Id로 RefreshToken 값 조회
    String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

    // 4.  RefreshToken이 null 이거나 Empty일 경우, 예외 발생
    if(refreshToken == null || refreshToken.isEmpty()){
      throw new LogoutUserException();
    }

    // 5. Refresh Token 일치하는지 검사
    if(!refreshToken.equals(tokenRequestDto.refreshToken())){
      throw new NotMatchTokenUserException();
    }

    // 6. 새로운 토큰 생성
    TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

    // 7. 새로운 RefreshToken Redis에 저장
    saveRefreshTokenCache(authentication, tokenDto);

    // 8. 토큰 발급
    return tokenDto;
  }

}
