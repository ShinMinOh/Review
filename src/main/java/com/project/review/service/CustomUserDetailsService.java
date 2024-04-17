package com.project.review.service;

import com.project.review.model.Member;
import com.project.review.repository.MemberRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;
  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
    return memberRepository.findByEmail(userEmail)
        .map(this::createUserDetails) // null이 아닐경우
        .orElseThrow(() -> new UsernameNotFoundException(userEmail+"-> 데이터베이스에서 찾을 수 없습니다"));
  }

  // DB에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
  private UserDetails createUserDetails(Member member){
    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
        member.getAuthority().toString());

    // UserDetails 리턴
    return new User(String.valueOf(member.getId()),
                    member.getPassword(),
                    Collections.singleton(grantedAuthority)
    );

  }
}
