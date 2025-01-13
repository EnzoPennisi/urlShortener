package com.example.urlShortener.controller;

import com.example.urlShortener.dto.UrlRequestDto;
import com.example.urlShortener.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
@AllArgsConstructor
public class UrlController {

    private final UrlService urlService;

    // GET: http://localhost:8080/api/url/{idUser}
    @GetMapping("/{idUser}")
    public ResponseEntity<?> getUrlsByUser(@PathVariable Long idUser) {
        try {
            return ResponseEntity.ok(urlService.findByUserId(idUser));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al obtener las urls");
        }
    }

    // POST: http://localhost:8080/api/url/shorten/
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody UrlRequestDto urlRequestDto) {
        try {
            String shortenedLink = urlService.saveShortUrl(urlRequestDto);
            return ResponseEntity.ok(shortenedLink);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al acortar el link");
        }
    }

    // PATCH: http://localhost:8080/api/url/update/{idUrl}
    @PatchMapping("/update/{idUrl}")
    public ResponseEntity<?> updateUrl(@PathVariable Long idUrl, @RequestBody UrlRequestDto urlRequestDto) {
        try {
            urlService.updateUrl(idUrl, urlRequestDto);
            return ResponseEntity.ok("Url actualizada");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al actualizar la url");
        }
    }

    // DELETE: http://localhost:8080/api/url/delete/
    @DeleteMapping("/delete/{idUrl}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long idUrl) {
        try {
            urlService.deleteUrl(idUrl);
            return ResponseEntity.ok("Url eliminada");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al eliminar la url");
        }
    }
}
