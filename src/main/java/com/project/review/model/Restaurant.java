package com.project.review.model;

import com.project.review.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")    // 단방향 매핑
  private Member member;

  @Column(name = "restaurant_name")
  private String name;

  @Column(name = "restaurant_address")
  private String address;

  @Builder
  public Restaurant(Member member, String name, String address) {
    this.member = member;
    this.name = name;
    this.address = address;
  }

  public void modifyInfo(Restaurant modifiedEntity) {
    if ((modifiedEntity.name != null)) {
      this.name = modifiedEntity.name;
    }
    if (modifiedEntity.address != null) {
      this.address = modifiedEntity.address;
    }
  }
}
