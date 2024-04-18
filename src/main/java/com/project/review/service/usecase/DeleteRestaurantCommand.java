package com.project.review.service.usecase;

public record DeleteRestaurantCommand(
    Long memberId,
    Long restaurantId
) {

}
