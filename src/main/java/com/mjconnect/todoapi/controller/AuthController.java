package com.mjconnect.todoapi.controller;

import com.mjconnect.todoapi.config.JwtUtil;
import com.mjconnect.todoapi.dto.AuthRequest;
import com.mjconnect.todoapi.dto.RegisterRequest;
import com.mjconnect.todoapi.entity.TbUser;
import com.mjconnect.todoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt;
    }

    @PostMapping("/register")
    public TbUser registerUser(@RequestBody RegisterRequest registerRequest) {
        return userService.registerNewUser(registerRequest);
    }

}
