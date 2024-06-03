package com.project.review.jwt;

import static org.junit.jupiter.api.Assertions.*;

import com.project.review.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

class TokenProviderTest {

  @Test
  void 토큰생성(){
    byte[] keyBytes = Decoders.BASE64.decode("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");
    Key key = Keys.hmacShaKeyFor(keyBytes);

    long now = (new Date()).getTime();

    // Access Token 생성
    String accessToken = Jwts.builder()                                         // JwtBuilder 객체를 생성 하고, Jwts.builder() 메서드를 이용
        .setSubject("2")                                                    //payload "sub": "1"
        .claim("auth", "ROLE_USER")                                    //payload "auth": "ROLE_ADMIN"
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

    System.out.println(accessToken);
    //
  }
}