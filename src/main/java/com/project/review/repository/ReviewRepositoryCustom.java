package com.project.review.repository;

import com.project.review.model.Review;
import com.project.review.service.usecase.ReviewDetailDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewRepositoryCustom {

  Double getAvgScoreByRestaurantId(Long restaurantId);

  Slice<ReviewDetailDto> findSliceByRestaurantId(Long restaurantId, Pageable pageable);

}
