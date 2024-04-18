package com.project.review.controller.request;

import com.project.review.service.usecase.ModifyRestaurantCommand;
import java.util.List;

public record ModifyRestaurantRequestDto(
    String name,
    String address,
    List<SaveMenuRequestDto> menus
) {
  public ModifyRestaurantCommand toCommand(Long memberId, Long restaurantId){
    return new ModifyRestaurantCommand(memberId,restaurantId,name,address,menus);
  }
}
