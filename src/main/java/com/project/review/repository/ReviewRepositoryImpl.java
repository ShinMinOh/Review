package com.project.review.repository;


import static com.project.review.model.QReview.review;


import com.project.review.model.Review;
import com.project.review.service.usecase.ReviewDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;


  @Override
  public Double getAvgScoreByRestaurantId(Long restaurantId) {
    return jpaQueryFactory.select(review.score.avg())
        .from(review)
        .where(review.restaurant.id.eq(restaurantId))
        .fetchFirst();
  }


  @Override
  public Slice<ReviewDetailDto> findSliceByRestaurantId(Long restaurantId, Pageable pageable) {
    List<ReviewDetailDto> reviews = jpaQueryFactory
        .select(Projections.fields(ReviewDetailDto.class,
            review.id.as("reviewId"),
            review.content,
            review.score,
            review.createdAt))
        .from(review)
        .where(review.restaurant.id.eq(restaurantId))
        .offset((long) pageable.getPageNumber() * pageable.getPageSize())
        .limit(pageable.getPageSize() + 1)
        .fetch();

    return new SliceImpl<>(reviews.stream().limit(pageable.getPageSize()).toList(),
        pageable,
        reviews.size() > pageable.getPageSize());
  }


}
