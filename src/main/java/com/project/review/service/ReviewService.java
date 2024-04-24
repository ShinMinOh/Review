package com.project.review.service;

import com.project.review.model.Member;
import com.project.review.model.Restaurant;
import com.project.review.model.Review;
import com.project.review.repository.MemberRepository;
import com.project.review.repository.RestaurantRepository;
import com.project.review.repository.ReviewRepository;
import com.project.review.service.exception.InvalidReviewException;
import com.project.review.service.exception.RestaurantNotFoundException;
import com.project.review.service.exception.ReviewNotFoundException;
import com.project.review.service.exception.UserNotFoundException;
import com.project.review.service.usecase.DeleteReviewCommand;
import com.project.review.service.usecase.ReviewDetailDto;
import com.project.review.service.usecase.ReviewDto;

import com.project.review.service.usecase.ReviewPageDto;
import com.project.review.service.usecase.SaveReviewCommand;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

  private final RestaurantRepository restaurantRepository;
  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;

  @Transactional
  public Review createReview(SaveReviewCommand saveReviewCommand){
    Member member = findMember(saveReviewCommand.memberId());
    Restaurant restaurantToAddReview = findRestaurant(saveReviewCommand.restaurantId());

    Review review = Review.createReview(restaurantToAddReview, member, saveReviewCommand.content(),
        saveReviewCommand.score());

    Review savedReview = saveReview(review);

    return savedReview;
  }

  @Transactional
  public void deleteReview(DeleteReviewCommand deleteReviewCommand){
    Review reviewToDelete = findReview(deleteReviewCommand.reviewId());

    Restaurant restaurant = findRestaurant(deleteReviewCommand.restaurantId());

    // 삭제하려는 review의 memberId 와 restaurantId 모두가 일치해야만 지울수 있음
    if(!reviewToDelete.validateRelation(
        deleteReviewCommand.memberId(),
        deleteReviewCommand.restaurantId()
    )){
      throw new InvalidReviewException();
    }

    deleteReview(reviewToDelete);
  }

  @Transactional(readOnly = true)
  public ReviewDto getRestaurantReview(Long restaurantId, Pageable pageable){
    Double avgScore = reviewRepository.getAvgScoreByRestaurantId(restaurantId);
    Slice<ReviewDetailDto> reviews = reviewRepository.findSliceByRestaurantId(
        restaurantId, pageable);

    ReviewDto totalReviews = ReviewDto.builder().avgScore(avgScore)
        .reviews(reviews.getContent())
        .page(ReviewPageDto.builder()
            .offset(pageable.getPageNumber() * pageable.getPageSize())
            .limit(pageable.getPageSize())
            .build()
        ).build();

    return totalReviews;
  }

  private void deleteReview(Review reviewToDelete) {
    reviewRepository.deleteById(reviewToDelete.getId());
  }

  private Review findReview(Long reviewId) {
    return reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ReviewNotFoundException(reviewId));
  }


  private Review saveReview(Review review) {
    return reviewRepository.save(review);
  }

  private Restaurant findRestaurant(Long restaurantId) {
    return restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
  }

  private Member findMember(Long memberId) {
    return memberRepository.findById(memberId).orElseThrow(() -> new UserNotFoundException(memberId));
  }
}
