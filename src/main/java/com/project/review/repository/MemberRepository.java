package com.project.review.repository;

import com.project.review.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

  boolean existsByEmail(String email);

  Optional<Member> findByEmail(String email);
}
