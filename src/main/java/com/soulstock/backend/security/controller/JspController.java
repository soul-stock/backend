package com.soulstock.backend.security.controller;

import com.soulstock.backend.security.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class JspController {
    private final AuthService authService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        Cookie jwtCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        if (jwtCookie != null) {
            try {
                String token = jwtCookie.getValue();
                String email = authService.getEmailFromJwtToken(token);
                model.addAttribute("email", email);
            } catch (Exception e) {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
        return "dashboard";
    }
}
