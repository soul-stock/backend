package com.soulstock.backend.security;

import com.soulstock.backend.security.dto.UserDto;
import com.soulstock.backend.security.jwt.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> loginRequest,
            HttpServletResponse response
    ) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<UserDto> user = authService.findByUsername(username);

        if (user.isPresent() && authService.validatePassword(password, user.get().getPassword())) {
            String jwtToken = jwtTokenUtil.generateJwtToken(username);

            Cookie jwtCookie = new Cookie("jwt", jwtToken);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(24 * 60 * 60);
            jwtCookie.setPath("/");
            jwtCookie.setSecure(false);
            jwtCookie.setAttribute("SameSite", "Strict");
            response.addCookie(jwtCookie);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", true);
            responseBody.put("token", jwtToken);
            responseBody.put("username", username);

            return ResponseEntity.ok(responseBody);
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(UserDto userDto) {
        authService.registerUser(userDto);
        return ResponseEntity.ok("success");
    }
}