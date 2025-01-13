package com.example.urlShortener.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties("urls")  // Evita la recursión infinita
    private User user;

    public Url(String originalLink, String shortenedLink, User user) {
        this.originalLink = originalLink;
        this.shortenedLink = shortenedLink;
        this.user = user;
    }
}
