package com.projectSpring.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawpassword = "password";
        String encodePassword = encoder.encode(rawpassword);

        System.out.println(encodePassword);
    }
}
