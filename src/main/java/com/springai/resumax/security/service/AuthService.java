package com.springai.resumax.security.service;

import com.springai.resumax.security.dto.LoginDto;
import com.springai.resumax.security.dto.SignupDto;
import com.springai.resumax.security.entity.User;
import com.springai.resumax.security.repository.UserRepository;
import com.springai.resumax.security.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final SecurityUtil securityUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User signup(SignupDto dto){

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return repository.save(user);

    }

    public String login(LoginDto dto){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        return securityUtil.generateToken(user);
    }
}
