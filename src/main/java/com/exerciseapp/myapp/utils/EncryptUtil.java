package com.exerciseapp.myapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtil {

    private static final BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();

    public static String Encrypt(String s) {
        return encrypt.encode(s);
    }
}
