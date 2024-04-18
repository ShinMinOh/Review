package com.project.review.service.usecase;

import com.project.review.controller.request.SaveMenuRequestDto;
import com.project.review.model.Member;
import com.project.review.model.Restaurant;
import java.util.List;

public record SaveRestaurantCommand(
    String name,
    String address,
    List<SaveMenuRequestDto> menus,
    Long memberId
) {
  public Restaurant toEntity(Member member) {
    return Restaurant.builder()
        .member(member)
        .name(name)
        .address(address)
        .build();

  }

}
