package com.project.review.controller.response;

import com.project.review.controller.request.SaveMenuRequestDto;
import com.project.review.service.usecase.DetailRestaurantDto;
import com.project.review.service.usecase.RestaurantDto;
import java.time.LocalDateTime;
import java.util.List;

public record RestaurantDetailResponseDto(
    Long restaurantId,
    String restaurantName,
    String address,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    List<Menu> menus

) {
  public record Menu(
      Long menuId,
      String name,
      Integer price,
      LocalDateTime createdAt,
      LocalDateTime modifiedAt
  ) {

  }

    public static RestaurantDetailResponseDto toResponse(DetailRestaurantDto detailRestaurantDto){
      List<Menu> menu = detailRestaurantDto.getMenus()
          .stream().map(dto -> new Menu(
              dto.getId(),
              dto.getName(),
              dto.getPrice(),
              dto.getCreatedAt(),
              dto.getModifiedAt()
              ))
          .toList();

      return new RestaurantDetailResponseDto(
          detailRestaurantDto.getRestaurantId(),
          detailRestaurantDto.getRestaurantName(),
          detailRestaurantDto.getAddress(),
          detailRestaurantDto.getCreatedAt(),
          detailRestaurantDto.getModifiedAt(),
          menu
      );
    }
}
