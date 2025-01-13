package com.example.urlShortener.service;

import com.example.urlShortener.entity.User;

public interface UserService {
    User findByUsername(String username) throws Exception;
    User save(User user)throws Exception;
}
