package com.project.review.jwt;

import static org.junit.jupiter.api.Assertions.*;

import com.project.review.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

class TokenProviderTest {

  @Test
  void 토큰생성(){
    byte[] keyBytes = Decoders.BASE64.decode("yUwGn6KqD2n7i9hO2ZejLJAxABKeYhRNQ2QGX4GX28g1SblKzPtS5O2Pd5CdrUx7nRzS5pGUCqjgApHjQdpBxh3Hq");
    SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

    long now = (new Date()).getTime();

    // Access Token 생성
    String accessToken = Jwts.builder()                                         // JwtBuilder 객체를 생성 하고, Jwts.builder() 메서드를 이용
        .setSubject("1")                                                    //payload "sub": "1"
        .claim("auth", "ROLE_ADMIN")                                    //payload "auth": "ROLE_ADMIN"
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();

    System.out.println(accessToken);
    //
  }
}