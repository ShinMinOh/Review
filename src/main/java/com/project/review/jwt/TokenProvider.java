package com.project.review.jwt;

import com.project.review.dto.TokenDto;
import com.project.review.jwt.exception.NotAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {
  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_TYPE = "Bearer";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 3;  // 3일

  private final Key key;

  public TokenProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * 토큰 생성
   * */
  public TokenDto generateTokenDto(Authentication authentication){
    // 권한들 가져오기
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();

    // Access Token 생성
    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()                                         // JwtBuilder 객체를 생성 하고, Jwts.builder() 메서드를 이용
        .setSubject(authentication.getName())                                   //payload "sub": "1"
        .claim(AUTHORITIES_KEY, authorities)                                    //payload "auth": "ROLE_ADMIN"
        .setExpiration(accessTokenExpiresIn)                                    //payload "exp": 1805752058
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    // Refresh Token 생성
    Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
    String refreshToken = Jwts.builder()
        .setExpiration(refreshTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    return new TokenDto(BEARER_TYPE, accessToken, refreshToken, accessTokenExpiresIn.getTime());
  }

  public Authentication getAuthentication(String accessToken){
    // 토큰 복호화
    Claims claims = parseClaims(accessToken);

    if(claims.get(AUTHORITIES_KEY) == null){
      throw new NotAuthorizedException();
    }

    // 클레임에서 권한 정보 가져오기
    List<SimpleGrantedAuthority> authorities = Arrays.stream(
            claims.get(AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .toList();

    // UserDetails 객체를 만들어서 Authentication 리턴
    UserDetails principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public boolean validateToken(String token){
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
        log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
        log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
        log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
        log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }


  /**
   * Access Token의 Payload 부분 추출
   * */
  private Claims parseClaims(String accessToken){
    try{
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    }catch (ExpiredJwtException e){
      return e.getClaims();
    }
  }


}
