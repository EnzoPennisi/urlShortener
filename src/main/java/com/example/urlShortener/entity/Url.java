package com.example.urlShortener.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalLink;
    @Column(unique = true)
    private String shortenedLink;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int accessCount;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties("urls")  // Evita la recursión infinita
    private User user;

    public Url(String originalLink, String shortenedLink,String description, User user) {
        this.originalLink = originalLink;
        this.shortenedLink = shortenedLink;
        this.description = description;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.accessCount = 0;
    }
}
