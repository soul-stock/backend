package com.soulstock.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping("/")
    public ResponseEntity<String> test() {
        String result = "Success Access Test API";
        return ResponseEntity.ok(result);
    }
}
