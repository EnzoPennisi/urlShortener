package com.example.urlShortener.dto;

public record UrlShortResponseDto(Long id, String originalLink, String shortenedLink) {
    public static UrlShortResponseDto fromEntity(com.example.urlShortener.entity.Url url) {
        return new UrlShortResponseDto(url.getId(), url.getOriginalLink(), url.getShortenedLink());
    }
}
