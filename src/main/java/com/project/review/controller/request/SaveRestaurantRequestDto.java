package com.project.review.controller.request;

import com.project.review.service.usecase.SaveRestaurantCommand;
import java.util.List;

public record SaveRestaurantRequestDto(
    String name,
    String address,
    List<SaveMenuRequestDto> menus
) {
  public SaveRestaurantCommand toCommand(Long userId){
    return new SaveRestaurantCommand(name, address, menus, userId);
  }
}
