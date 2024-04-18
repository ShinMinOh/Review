package com.project.review.service.usecase;

import com.project.review.controller.request.SaveMenuRequestDto;
import com.project.review.model.Restaurant;
import java.util.List;

public record ModifyRestaurantCommand(
    Long memberId,
    Long restaurantId,
    String name,
    String address,
    List<SaveMenuRequestDto> menus
) {

  public Restaurant toModifiedEntity(){
    return Restaurant.builder()
        .name(name)
        .address(address)
        .build();
  }
}
