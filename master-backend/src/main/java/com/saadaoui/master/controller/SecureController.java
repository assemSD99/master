package com.saadaoui.master.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/protected")
    public String getProtectedData() {
        return "Ceci est une donnée protégée.";
    }
}

