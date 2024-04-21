package com.project.review.service.usecase;

import com.project.review.model.Menu;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DetailRestaurantDto {
  private Long restaurantId;
  private String restaurantName;
  private String address;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  private List<Menu> menus;

  @Builder
  public DetailRestaurantDto(Long restaurantId, String restaurantName, String address,
      LocalDateTime createdAt, LocalDateTime modifiedAt, List<Menu> menus) {
    this.restaurantId = restaurantId;
    this.restaurantName = restaurantName;
    this.address = address;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
    this.menus = menus;
  }
}
