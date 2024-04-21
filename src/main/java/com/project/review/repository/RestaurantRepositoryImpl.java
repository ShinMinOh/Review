package com.project.review.repository;

import static com.project.review.model.QRestaurant.restaurant;

import com.project.review.service.usecase.DetailRestaurantDto;
import com.project.review.service.usecase.RestaurantDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;


  @Transactional(readOnly = true)
  @Override
  public Optional<DetailRestaurantDto> findRestaurantByRestaurantId(Long restaurantId){
    return Optional.ofNullable(jpaQueryFactory
        .select(Projections.fields(DetailRestaurantDto.class,
            restaurant.id.as("restaurantId"),
            restaurant.name.as("restaurantName"),
            restaurant.address,
            restaurant.createdAt,
            restaurant.modifiedAt))
        .from(restaurant)
        .where(restaurant.id.eq(restaurantId))
        .fetchOne());
  }



}
