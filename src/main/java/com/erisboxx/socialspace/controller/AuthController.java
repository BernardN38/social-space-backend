package com.erisboxx.socialspace.controller;

import com.erisboxx.socialspace.entity.Role;
import com.erisboxx.socialspace.entity.User;
import com.erisboxx.socialspace.payload.JwtAuthResponse;
import com.erisboxx.socialspace.payload.LoginDto;
import com.erisboxx.socialspace.payload.SignupDto;
import com.erisboxx.socialspace.repository.RoleRepository;
import com.erisboxx.socialspace.repository.UserRepository;
import com.erisboxx.socialspace.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/testpost")
    public ResponseEntity<String> testPost(HttpServletResponse response, HttpServletRequest request) {
        Cookie name = WebUtils.getCookie(request, "jwt-token");
        if (name == null) {
            return ResponseEntity.ok("No cookie found");
        }


        return ResponseEntity.ok(name.getValue());
    }


    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateuser(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        System.out.println(authentication.getDetails());
        String token = tokenProvider.generateToken(authentication);

        Cookie cookie = new Cookie("jwt-token", token);
        cookie.setMaxAge(900);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
        jwtAuthResponse.setUsername(authentication.getName());
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signupDto) {
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);
        return new ResponseEntity<>("User Successfully registered", HttpStatus.CREATED);
    }
}
