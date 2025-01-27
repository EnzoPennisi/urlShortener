package com.example.urlShortener.service;

import com.example.urlShortener.dto.UserResponseDto;
import com.example.urlShortener.entity.User;
import com.example.urlShortener.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto findByUsername(String username) throws Exception {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

            return UserResponseDto.fromEntity(user);
    }

    @Override
    public User save(User user) throws Exception {
        try{
            return userRepository.save(user);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}

