package com.lifeIsBeautiful.blogrestapi.controller;


import com.lifeIsBeautiful.blogrestapi.payloads.JwtAuthResponse;
import com.lifeIsBeautiful.blogrestapi.payloads.LoginDto;
import com.lifeIsBeautiful.blogrestapi.payloads.RegisterDto;
import com.lifeIsBeautiful.blogrestapi.security.JwtTokenProvider;
import com.lifeIsBeautiful.blogrestapi.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
   private    AuthService authService;

    @PostMapping(value = {"login","sign-in"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
    {
       String token=  authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);

     //  return new ResponseEntity<>(authuser, HttpStatus.OK);
    }

    @PostMapping(value = {"register","sign-up"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
    {
       String registerUser = authService.register(registerDto);
       return new ResponseEntity<>(registerUser,HttpStatus.CREATED);
    }

}
