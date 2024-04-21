package com.project.review.service;

import com.project.review.controller.request.SaveMenuRequestDto;
import com.project.review.controller.response.AllRestaurantsResponseDto;
import com.project.review.model.Member;
import com.project.review.model.Menu;
import com.project.review.model.Restaurant;
import com.project.review.repository.MemberRepository;
import com.project.review.repository.MenuRepository;
import com.project.review.repository.RestaurantRepository;
import com.project.review.service.exception.InvalidRestaurantException;
import com.project.review.service.exception.RestaurantNotFoundException;
import com.project.review.service.exception.UserNotFoundException;
import com.project.review.service.usecase.DeleteRestaurantCommand;
import com.project.review.service.usecase.DetailRestaurantDto;
import com.project.review.service.usecase.ModifyRestaurantCommand;
import com.project.review.service.usecase.RestaurantDto;
import com.project.review.service.usecase.SaveRestaurantCommand;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RestaurantService {

  private final MemberRepository memberRepository;
  private final RestaurantRepository restaurantRepository;
  private final MenuRepository menuRepository;

  @Transactional
  public Restaurant createRestaurant(SaveRestaurantCommand saveRestaurantCommand){
    Member member = findMember(saveRestaurantCommand.memberId());

    Restaurant restaurantToSave = saveRestaurant(saveRestaurantCommand.toEntity(member));

    saveRestaurantCommand.menus().forEach((menu) ->{
      Menu menuToSave = makeMenu(menu, restaurantToSave);

      menuRepository.save(menuToSave);
    });

    return restaurantToSave;
  }

  @Transactional(readOnly = true)
  public List<RestaurantDto> getAllRestaurants(){
    List<Restaurant> restaurants = restaurantRepository.findAll();

    List<RestaurantDto> restaurantList = restaurants.stream().map((restaurant) -> RestaurantDto.builder()
        .restaurantId(restaurant.getId())
        .restaurantName(restaurant.getName())
        .address(restaurant.getAddress())
        .createdAt(restaurant.getCreatedAt())
        .modifiedAt(restaurant.getModifiedAt())
        .build()).toList();

    return restaurantList;
  }

  @Transactional(readOnly = true)
  public DetailRestaurantDto getSingleRestaurant(Long restaurantId){
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(()-> new RestaurantNotFoundException(restaurantId));

    List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);

    DetailRestaurantDto DetailsOfRestaurant = DetailRestaurantDto.builder()
        .restaurantId(restaurant.getId())
        .restaurantName(restaurant.getName())
        .address(restaurant.getAddress())
        .createdAt(restaurant.getCreatedAt())
        .modifiedAt(restaurant.getModifiedAt())
        .menus(menus)
        .build();

    return DetailsOfRestaurant;
  }

  @Transactional
  public void modifyRestaurant(ModifyRestaurantCommand modifyRestaurantCommand){
    //레스토랑 정보 수정
    Restaurant restaurantToModify = findRestaurant(modifyRestaurantCommand.restaurantId());

    if(!restaurantToModify.getMember().getId().equals(modifyRestaurantCommand.memberId())){
      throw new InvalidRestaurantException();
    }
    restaurantToModify.modifyInfo(modifyRestaurantCommand.toModifiedEntity());
    restaurantRepository.save(restaurantToModify);

    // 레스토랑과 연관된 메뉴 수정
    List<Menu> menus = menuRepository.findAllByRestaurantId(modifyRestaurantCommand.restaurantId());
    menuRepository.deleteAll(menus);

    modifyRestaurantCommand.menus().forEach((menu) -> {
      Menu menuToModify = Menu.builder()
          .restaurant(restaurantToModify)
          .name(menu.name())
          .price(menu.price())
          .build();

      menuRepository.save(menuToModify);
    });
  }

  @Transactional
  public void deleteRestaurant(DeleteRestaurantCommand deleteRestaurantCommand){
    Restaurant restaurantToDelete = findRestaurant(deleteRestaurantCommand.restaurantId());

    if(!restaurantToDelete.getMember().getId().equals(deleteRestaurantCommand.memberId())){
      throw new InvalidRestaurantException();
    }
    // 외래 키 제약조건때문에 menu 먼저 삭제
    List<Menu> menus = menuRepository.findAllByRestaurantId(deleteRestaurantCommand.restaurantId());
    menuRepository.deleteAll(menus);

    // 레스토랑 삭제
    restaurantRepository.delete(restaurantToDelete);
  }

  private Restaurant findRestaurant(Long restaurantId) {
    return restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
  }

  private static Menu makeMenu(SaveMenuRequestDto menu, Restaurant restaurantToSave) {
    return Menu.builder()
        .restaurant(restaurantToSave)
        .name(menu.name())
        .price(menu.price())
        .build();
  }

  private Restaurant saveRestaurant(Restaurant restaurant) {
    return restaurantRepository.save(restaurant);
  }

  private Member findMember(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new UserNotFoundException(memberId));
  }
}
