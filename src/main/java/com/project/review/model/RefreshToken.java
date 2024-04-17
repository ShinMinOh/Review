package com.project.review.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

  @Id
  @Column(name = "refresh_token_key")
  private String key;

  @Column(name = "refresh_token_value")
  private String value;

  @Builder
  public RefreshToken(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public RefreshToken updateValue(String token){
    this.value = value;
    return this;
  }
}
