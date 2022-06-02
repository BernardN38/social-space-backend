package com.erisboxx.socialspace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/api")
public class TestController {

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/testauth")
    public ResponseEntity<String> testAuth(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        return ResponseEntity.ok("auth successful");
    }
}
