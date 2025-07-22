package com.soulstock.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String index() {
        System.out.println("Access to login");
        return "login";
    }
}