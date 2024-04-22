package com.project.review.model;

import com.project.review.common.model.BaseCreatedTime;
import com.project.review.service.usecase.SaveReviewCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseCreatedTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")    // 단방향 매핑
  private Member writer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id")    // 단방향 매핑
  private Restaurant restaurant;


  private String content;

  private Double score;

  @Builder
  public Review(Member writer, Restaurant restaurant, String content, Double score) {
    this.writer = writer;
    this.restaurant = restaurant;
    this.content = content;
    this.score = score;
  }

  public static Review createReview(Restaurant restaurantToAddReview, Member member, String content, Double score) {
    return Review.builder()
        .writer(member)
        .restaurant(restaurantToAddReview)
        .content(content)
        .score(score)
        .build();
  }

  public boolean validateRelation(Long memberId, Long restaurantId) {
    return this.writer.getId().equals(memberId) && this.restaurant.getId().equals(restaurantId);
  }
}
