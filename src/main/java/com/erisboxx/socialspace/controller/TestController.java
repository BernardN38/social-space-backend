package com.erisboxx.socialspace.controller;

import com.erisboxx.socialspace.entity.User;
import com.erisboxx.socialspace.exception.ResourceNotFoundException;
import com.erisboxx.socialspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;


@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/testauth")
    public ResponseEntity<String> testAuth(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findById(1L).orElseThrow(()->new ResourceNotFoundException("user","id", 1L));
        User userToFind = userRepository.findById(4991L).orElseThrow(()->new ResourceNotFoundException("user","id", 2L));
        System.out.println(user.getName());
        System.out.println(userToFind.getName());
        System.out.println(user.getFriends().contains(userToFind));

        return ResponseEntity.ok("auth successful");
    }
}
