package com.project.review.service.usecase;

import com.project.review.controller.request.SaveMenuRequestDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantDto {

  private Long restaurantId;
  private String restaurantName;
  private String address;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;


  @Builder
  public RestaurantDto(Long restaurantId, String restaurantName, String address,
      LocalDateTime createdAt, LocalDateTime modifiedAt) {
    this.restaurantId = restaurantId;
    this.restaurantName = restaurantName;
    this.address = address;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;

  }
}
