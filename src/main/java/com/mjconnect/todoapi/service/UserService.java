package com.mjconnect.todoapi.service;

import com.mjconnect.todoapi.dto.RegisterRequest;
import com.mjconnect.todoapi.entity.TbUser;
import com.mjconnect.todoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TbUser registerNewUser(RegisterRequest registerRequest) {
        TbUser user = new TbUser();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        user.setAuthority(registerRequest.getAuthority());
        return userRepository.save(user);
    }
}
