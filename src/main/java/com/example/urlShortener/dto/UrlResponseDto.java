package com.example.urlShortener.dto;

import com.example.urlShortener.entity.Url;

public record UrlResponseDto(Long id, String originalLink, String shortenedLink) {
    public static UrlResponseDto fromEntity(Url url) {
        return new UrlResponseDto(url.getId(), url.getOriginalLink(), url.getShortenedLink());
    }
}

