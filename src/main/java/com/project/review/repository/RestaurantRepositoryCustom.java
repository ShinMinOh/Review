package com.project.review.repository;

import com.project.review.service.usecase.DetailRestaurantDto;
import com.project.review.service.usecase.RestaurantDto;
import java.util.Optional;

public interface RestaurantRepositoryCustom {
  Optional<DetailRestaurantDto> findRestaurantByRestaurantId(Long restaurantId);

}
