package com.project.review.service.usecase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewPageDto {
  private Integer offset;
  private Integer limit;

  @Builder
  public ReviewPageDto(Integer offset, Integer limit) {
    this.offset = offset;
    this.limit = limit;
  }
}
