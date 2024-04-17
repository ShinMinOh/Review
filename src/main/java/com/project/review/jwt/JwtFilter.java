package com.project.review.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";

  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // 1. Request Header에서 토큰을 꺼냄
    String jwt = resolveToken(request);

    // 2. validateToken으로 유효성 검사
    // 정상 토큰이면 해당 토큰으로 Authentication 가져와  SecurityContext에 저장
    if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
      Authentication authentication = tokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request,response);
  }


  // Request Header 에서 토큰 정보 꺼내오기
  private String resolveToken(HttpServletRequest request){
    // HTTP request -> Authorization: Bearer {토큰값}
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER); //Bearer {토큰값}
    if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
      return bearerToken.substring(7);
    }
    return null;
  }
}
