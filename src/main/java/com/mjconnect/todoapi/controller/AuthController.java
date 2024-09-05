package com.mjconnect.todoapi.controller;

import com.mjconnect.todoapi.config.JwtUtil;
import com.mjconnect.todoapi.dto.AuthRequest;
import com.mjconnect.todoapi.dto.GoogleUser;
import com.mjconnect.todoapi.dto.RegisterRequest;
import com.mjconnect.todoapi.entity.TbUser;
import com.mjconnect.todoapi.service.GoogleUserService;
import com.mjconnect.todoapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
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

    @Autowired
    private GoogleUserService googleUserService;

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

    @PostMapping("/google")
    public String googleLogin(@RequestBody Map<String, String> body) {
        String accessToken = body.get("token");
        log.info(accessToken);
        // Google 토큰을 검증하고 사용자 정보를 가져옴
        GoogleUser googleUser = googleUserService.getGoogleUserInfo(accessToken);

        // Google 사용자로 회원가입 또는 로그인 처리
        TbUser user = googleUserService.registerOrLoginGoogleUser(googleUser);
        // 사용자 정보를 기반으로 JWT 토큰 발급
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        log.info(userDetails.toString());
        final String jwt = jwtUtil.generateToken(userDetails);
        log.info(jwt);
        return jwt;
    }

}
