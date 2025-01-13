package com.example.urlShortener.controller;

import com.example.urlShortener.entity.User;
import com.example.urlShortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try{
            return ResponseEntity.ok(userService.findByUsername(username));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al obtener el usuario");
        }
    }



    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.save(user));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al guardar el usuario");
        }
    }
}
