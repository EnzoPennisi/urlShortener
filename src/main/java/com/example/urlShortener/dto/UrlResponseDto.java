package com.example.urlShortener.dto;

import com.example.urlShortener.entity.Url;

import java.time.LocalDateTime;

public record UrlResponseDto(Long id, String originalLink, String shortenedLink, String description ,LocalDateTime createdAt, LocalDateTime updatedAt, int accessCount) {
    public static UrlResponseDto fromEntity(Url url) {
        return new UrlResponseDto(url.getId(), url.getOriginalLink(), url.getShortenedLink(), url.getDescription(), url.getCreatedAt(), url.getUpdatedAt(), url.getAccessCount());
    }
}

