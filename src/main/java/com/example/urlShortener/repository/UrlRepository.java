package com.example.urlShortener.repository;

import com.example.urlShortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findByUserId(Long userId);
    Optional<Url> findByShortenedLink(String shortenedLink);
    boolean existsByShortenedLink(String shortenedLink);
}

