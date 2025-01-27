package com.example.urlShortener.controller;

import com.example.urlShortener.entity.User;
import com.example.urlShortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    // GET: http://localhost:8080/api/user/{username}
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try{
            return ResponseEntity.ok(userService.findByUsername(username));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al obtener el usuario");
        }
    }


    // POST: http://localhost:8080/api/user
    @PostMapping("")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.save(user));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al guardar el usuario");
        }
    }
}
