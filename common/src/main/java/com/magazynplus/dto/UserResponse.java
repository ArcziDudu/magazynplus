package com.magazynplus.dto;

import lombok.Builder;

@Builder
public record UserResponse(Integer id,
                           String email,
                           String firstname,
                           String lastname) {
}
