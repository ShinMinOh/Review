package com.project.review.service.usecase;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDto {
  private Double avgScore;
  private List<ReviewDetailDto> reviews;
  private ReviewPageDto page;

  @Builder
  public ReviewDto(Double avgScore, List<ReviewDetailDto> reviews, ReviewPageDto page) {
    this.avgScore = avgScore;
    this.reviews = reviews;
    this.page = page;
  }
}
