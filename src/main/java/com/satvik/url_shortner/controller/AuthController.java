package com.satvik.url_shortner.controller;

import com.satvik.url_shortner.dto.LoginRequest;
import com.satvik.url_shortner.dto.RegisterRequest;
import com.satvik.url_shortner.entity.UserEntity;
import com.satvik.url_shortner.repository.UserRepository;
import com.satvik.url_shortner.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.satvik.url_shortner.entity.Role;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){
        UserEntity user = new UserEntity();
        user.setUsername( request.getUsername());
        user.setPassword(passwordEncoder.encode( request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return ResponseEntity.ok("Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }

         String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/protected-test")
    public String protectedTest() {
        return "You are authenticated!";
    }
}
