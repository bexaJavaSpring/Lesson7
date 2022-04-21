package com.example.lesson7.controller;

import com.example.lesson7.dto.LoginDto;
import com.example.lesson7.entity.User;
import com.example.lesson7.security.CurrentUser;
import com.example.lesson7.security.JwtProvider;
import com.example.lesson7.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDTO){
        String token=jwtProvider.generateToken(loginDTO.getUsername());
        return ResponseEntity.ok().body(token);
    }
    @GetMapping("/me")
    public HttpEntity<?> getMe(@CurrentUser User user) { //Parametr
        return ResponseEntity.ok().body("Mana" + user);
    }
}
