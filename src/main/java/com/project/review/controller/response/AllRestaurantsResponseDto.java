package com.project.review.controller.response;

import com.project.review.service.usecase.RestaurantDto;
import java.time.LocalDateTime;
import java.util.List;

public record AllRestaurantsResponseDto(
    Long restaurantId,
    String restaurantName,
    String address,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt

) {
  public static List<AllRestaurantsResponseDto> toResponse(List<RestaurantDto> restaurantDtos){
    return restaurantDtos.stream()
        .map(dto -> new AllRestaurantsResponseDto(
            dto.getRestaurantId(),
            dto.getRestaurantName(),
            dto.getAddress(),
            dto.getCreatedAt(),
            dto.getModifiedAt()
        ))
        .toList();
  }
}
