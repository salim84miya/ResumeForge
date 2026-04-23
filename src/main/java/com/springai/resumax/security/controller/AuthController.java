package com.springai.resumax.security.controller;

import com.springai.resumax.security.dto.LoginDto;
import com.springai.resumax.security.dto.SignupDto;
import com.springai.resumax.security.service.AuthService;
import com.springai.resumax.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupDto dto){

        var response = authService.signup(dto);

        return ResponseHandler.builder(response,null, HttpStatus.OK, LocalDateTime.now());
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){

        var response = authService.login(dto);

        return ResponseHandler.builder(response,null, HttpStatus.OK, LocalDateTime.now());

    }
}
