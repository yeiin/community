package com.ktb.community.global.encrypt;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptEncoder {

    private final BCryptPasswordEncoder encoder;

    public EncryptEncoder(final BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encrypt(String string) {
        return encoder.encode(string);
    }

    public boolean isMatch(String string, String encodedString) {
        return encoder.matches(string, encodedString);
    }

}
