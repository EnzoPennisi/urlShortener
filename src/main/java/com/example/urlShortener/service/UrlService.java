package com.example.urlShortener.service;

import com.example.urlShortener.dto.UrlRequestDto;
import com.example.urlShortener.dto.UrlResponseDto;
import com.example.urlShortener.entity.Url;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    List<UrlResponseDto> findByUserId(Long userId) throws Exception;
    String saveShortUrl(UrlRequestDto urlRequestDto) throws Exception;
    Optional<Url> findByShortenedLink(String shortenedLink) throws Exception;
    Url updateUrl(Long idUrl, UrlRequestDto urlResponseDto) throws Exception;
    boolean deleteUrl(Long idUrl) throws Exception;
}
