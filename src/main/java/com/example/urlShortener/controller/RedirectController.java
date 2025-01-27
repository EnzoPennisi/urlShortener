package com.example.urlShortener.controller;

import com.example.urlShortener.entity.Url;
import com.example.urlShortener.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class RedirectController {

    private final UrlService urlService;

    @GetMapping("/{shortenedLink}")
    public ResponseEntity<Object> redirectToOriginalLink(@PathVariable String shortenedLink) {

        try {
            Url url = urlService.findByShortenedLink(shortenedLink);

            urlService.incrementAccessCount(url);

            // Crear headers de redirección
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(url.getOriginalLink()));

            // Retornar respuesta 302 (Found/Redirect)
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL no encontrada");
        }
    }
}
