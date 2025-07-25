package com.soulstock.backend.security.controller;

import com.soulstock.backend.security.Service.AuthService;
import com.soulstock.backend.security.dto.LoginRequestDto;
import com.soulstock.backend.security.dto.RegisterRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto loginDto,
            HttpServletResponse response
    ) {
        String jwtToken = authService.login(loginDto);
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .maxAge(24 * 60 * 60)
                .path("/")
                .sameSite("Strict")
                .secure(false)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "email", loginDto.getEmail()
        ));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerDto) {
        try {
            authService.register(registerDto);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Welcome! Successfully registered."
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "You have been logged out."
        ));
    }
}