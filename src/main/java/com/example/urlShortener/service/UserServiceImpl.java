package com.example.urlShortener.service;

import com.example.urlShortener.entity.User;
import com.example.urlShortener.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) throws Exception {
        try{
            return userRepository.findByUsername(username);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

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

