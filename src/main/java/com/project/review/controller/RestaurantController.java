package com.project.review.controller;

import com.project.review.controller.request.ModifyRestaurantRequestDto;
import com.project.review.controller.request.SaveRestaurantRequestDto;
import com.project.review.controller.response.AllRestaurantsResponseDto;
import com.project.review.controller.response.RestaurantDetailResponseDto;
import com.project.review.model.Restaurant;
import com.project.review.service.RestaurantService;
import com.project.review.service.usecase.DeleteRestaurantCommand;
import com.project.review.service.usecase.DetailRestaurantDto;
import com.project.review.service.usecase.RestaurantDto;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@Validated
public class RestaurantController {

  private final RestaurantService restaurantService;

  // 맛집 등록 API
  @PostMapping("/restaurant")
  public ResponseEntity<Object> createRestaurant(
      @RequestBody SaveRestaurantRequestDto saveRestaurantRequestDto,
      @AuthenticationPrincipal UserDetails userDetails
  ){
    long userId = Long.parseLong(userDetails.getUsername());

    Restaurant restaurant = restaurantService.createRestaurant(
        saveRestaurantRequestDto.toCommand(userId));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(restaurant.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  // 맛집 전체 조회 API
  @GetMapping("/restaurants")
  public ResponseEntity<Object> getRestaurants(
  ) {
    List<RestaurantDto> allRestaurants = restaurantService.getAllRestaurants();

    List<AllRestaurantsResponseDto> responseList = AllRestaurantsResponseDto.toResponse(allRestaurants);

    return ResponseEntity.ok(responseList);
  }

  // 맛집 단일 조회 API
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<Object> getOneRestaurant(@PathVariable @Min(1) Long restaurantId){
    DetailRestaurantDto singleRestaurant = restaurantService.getSingleRestaurant(restaurantId);

    RestaurantDetailResponseDto responseDto = RestaurantDetailResponseDto.toResponse(singleRestaurant);

    return ResponseEntity.ok(responseDto);
  }

  // 맛집 정보 수정 API
  @PutMapping("/restaurant/{restaurantId}")
  public ResponseEntity<Object> modifyRestaurant(
      @PathVariable @Min(1) Long restaurantId,
      @RequestBody ModifyRestaurantRequestDto modifyRestaurantRequestDto,
      @AuthenticationPrincipal UserDetails userDetails
  ){
    long userId = Long.parseLong(userDetails.getUsername());

    restaurantService.modifyRestaurant(
        modifyRestaurantRequestDto.toCommand(userId,restaurantId));

    return ResponseEntity.ok().build();
  }

  // 맛집 삭제 API
  @DeleteMapping("/restaurant/{restaurantId}")
  public ResponseEntity<Object> deleteRestaurant(
      @PathVariable("restaurantId") @Min(1) Long restaurantId,
      @AuthenticationPrincipal UserDetails userDetails
  ){
    long userId = Long.parseLong(userDetails.getUsername());

    restaurantService.deleteRestaurant(new DeleteRestaurantCommand(userId,restaurantId));

    return ResponseEntity.noContent().build();
  }

}
