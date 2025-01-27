package com.example.urlShortener.service;

import com.example.urlShortener.dto.UrlRequestDto;
import com.example.urlShortener.dto.UrlResponseDto;
import com.example.urlShortener.dto.UrlShortResponseDto;
import com.example.urlShortener.entity.Url;

import java.util.List;

public interface UrlService {
    UrlResponseDto findById(Long id) throws Exception;
    List<UrlResponseDto> findByUserId(Long userId) throws Exception;
    UrlShortResponseDto saveShortUrl(UrlRequestDto urlRequestDto) throws Exception;
    Url findByShortenedLink(String shortenedLink) throws Exception;
    void incrementAccessCount(Url url) throws Exception;
    Url updateUrl(Long idUrl, UrlRequestDto urlResponseDto) throws Exception;
    boolean deleteUrl(Long idUrl) throws Exception;
}
