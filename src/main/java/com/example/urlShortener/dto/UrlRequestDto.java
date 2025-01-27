package com.example.urlShortener.dto;

public record UrlRequestDto(String originalLink, String description, Long idUser) {
}

