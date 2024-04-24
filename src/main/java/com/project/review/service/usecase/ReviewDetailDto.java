package com.project.review.service.usecase;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ReviewDetailDto {
  private Long reviewId;
  private String content;
  private Double score;
  private LocalDateTime createdAt;

  public ReviewDetailDto(Long reviewId, String content, Double score, LocalDateTime createdAt) {
    this.reviewId = reviewId;
    this.content = content;
    this.score = score;
    this.createdAt = createdAt;
  }
}

