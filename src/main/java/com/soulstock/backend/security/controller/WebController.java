package com.soulstock.backend.security.controller;

import com.soulstock.backend.security.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebController {
    private final AuthService authService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String email = getEmailFromJwtCookie(request);
        if (email != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userEmail", email);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(
            HttpServletRequest request,
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String message,
            Model model
    ) {
        if (getEmailFromJwtCookie(request) != null) {
            return "redirect:/dashboard";
        }
        if (error != null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
        }
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(
            HttpServletRequest request,
            @RequestParam(required = false) String message,
            Model model
    ) {
        if (getEmailFromJwtCookie(request) != null) {
            return "redirect:/dashboard";
        }
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        String email = getEmailFromJwtCookie(request);
        if (email == null) {
            log.warn("대시보드 접근 시도 - 인증되지 않은 사용자");
            redirectAttributes.addAttribute("error", "unauthorized");
            return "redirect:/login";
        }
        try {
            model.addAttribute("email", email);
            model.addAttribute("welcomeMessage", "안전하게 로그인되었습니다.");
            log.info(email);
            return "dashboard";
        } catch (Exception e) {
            log.error(e.getMessage());
            redirectAttributes.addAttribute("error", "session_expired");
            return "redirect:/login";
        }
    }

    /**
     * JWT Cookie
     */
    private String getEmailFromJwtCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .flatMap(this::extractEmailFromToken)
                .orElse(null);
    }

    private Optional<String> extractEmailFromToken(String token) {
        try {
            return Optional.ofNullable(authService.getEmailFromJwtToken(token));
        } catch (Exception e) {
            log.warn("JWT token extraction failed: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
