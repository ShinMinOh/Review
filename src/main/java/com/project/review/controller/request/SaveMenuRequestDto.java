package com.project.review.controller.request;

import java.time.LocalDateTime;

public record SaveMenuRequestDto(

    Long menuId,
    String name,
    Integer price,
    LocalDateTime createdAt,
    LocalDateTime modified
) {

}
