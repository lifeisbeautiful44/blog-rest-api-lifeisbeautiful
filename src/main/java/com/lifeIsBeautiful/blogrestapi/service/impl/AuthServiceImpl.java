package com.lifeIsBeautiful.blogrestapi.service.impl;


import com.lifeIsBeautiful.blogrestapi.entity.Role;
import com.lifeIsBeautiful.blogrestapi.entity.User;
import com.lifeIsBeautiful.blogrestapi.exception.BlogApiException;
import com.lifeIsBeautiful.blogrestapi.payloads.LoginDto;
import com.lifeIsBeautiful.blogrestapi.payloads.RegisterDto;
import com.lifeIsBeautiful.blogrestapi.repository.RoleRepository;
import com.lifeIsBeautiful.blogrestapi.repository.UserRepository;
import com.lifeIsBeautiful.blogrestapi.security.JwtTokenProvider;
import com.lifeIsBeautiful.blogrestapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        //check if username already exists in database;
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "The user with  the username " + registerDto.getUsername() + " already exists in the database.");

        }
        //check if email already exists in the database;
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "The user with the email " + registerDto.getEmail() + " already exists in the database.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roles.add(role);

        user.setRoles(roles);

        userRepository.save(user);

        return "User with username " + registerDto.getUsername() + " has been successfully registered.";


    }
}
