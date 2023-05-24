package com.lifeIsBeautiful.blogrestapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswrodGenerator {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        System.out.println( passwordEncoder.encode("srijansil"));
        System.out.println( passwordEncoder.encode("admin"));



    }
}
