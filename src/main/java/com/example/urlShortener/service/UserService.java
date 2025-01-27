package com.example.urlShortener.service;

import com.example.urlShortener.dto.UserResponseDto;
import com.example.urlShortener.entity.User;

public interface UserService {
    UserResponseDto findByUsername(String username) throws Exception;
    User save(User user)throws Exception;
}
