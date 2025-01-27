package com.example.urlShortener.dto;

public record UserResponseDto(Long id, String username) {
    public static UserResponseDto fromEntity(com.example.urlShortener.entity.User user) {
        return new UserResponseDto(user.getId(), user.getUsername());
    }
}
