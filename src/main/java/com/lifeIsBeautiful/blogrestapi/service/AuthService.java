package com.lifeIsBeautiful.blogrestapi.service;


import com.lifeIsBeautiful.blogrestapi.payloads.LoginDto;
import com.lifeIsBeautiful.blogrestapi.payloads.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
