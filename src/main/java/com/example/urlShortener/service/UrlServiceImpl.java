package com.example.urlShortener.service;

import com.example.urlShortener.dto.UrlRequestDto;
import com.example.urlShortener.dto.UrlResponseDto;
import com.example.urlShortener.dto.UrlShortResponseDto;
import com.example.urlShortener.entity.Url;
import com.example.urlShortener.entity.User;
import com.example.urlShortener.repository.UrlRepository;
import com.example.urlShortener.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    @Override
    public UrlResponseDto findById(Long id) throws Exception {
            Url url = urlRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Url no encontrada"));

            return UrlResponseDto.fromEntity(url);
    }

    @Override
    public List<UrlResponseDto> findByUserId(Long userId) throws Exception {
        try{
            List<Url> urls = urlRepository.findByUserId(userId);

            return urls.stream()
                    .map(UrlResponseDto::fromEntity)
                    .toList();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UrlShortResponseDto saveShortUrl(UrlRequestDto urlRequestDto) throws Exception {
        try{
            String shortened = generateShortenedLink();

            User user = userRepository.findById(urlRequestDto.idUser()).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

            Url url = new Url(urlRequestDto.originalLink(),shortened, urlRequestDto.description(),user);
            urlRepository.save(url);

            return UrlShortResponseDto.fromEntity(url);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Url findByShortenedLink(String shortenedLink) throws Exception {
        return urlRepository.findByShortenedLink(shortenedLink)
                .orElseThrow(() -> new NoSuchElementException("Url no encontrada"));
    }

    @Override
    public void incrementAccessCount(Url url) throws Exception {
        try {
            url.setAccessCount(url.getAccessCount() + 1);
            urlRepository.save(url);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Url updateUrl(Long idUrl, UrlRequestDto urlResponseDto) throws Exception {
        try {

            Url url = urlRepository.findById(idUrl).orElseThrow(() -> new NoSuchElementException("Url no encontrada"));

            url.setOriginalLink(urlResponseDto.originalLink());
            url.setDescription(urlResponseDto.description());
            url.setUpdatedAt(LocalDateTime.now());

            return urlRepository.save(url);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean deleteUrl(Long idUrl) throws Exception {
        try {
            if (!urlRepository.existsById(idUrl)) {
                throw new NoSuchElementException("No existe un url con ese Id");
            }

            urlRepository.deleteById(idUrl);
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private String generateShortenedLink() {
        String shortened;

        do {
            shortened = RandomStringUtils.randomAlphanumeric(6);
        } while (urlRepository.existsByShortenedLink(shortened));

        return shortened;
    }
}

