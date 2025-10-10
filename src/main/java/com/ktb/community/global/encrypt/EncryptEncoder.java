package com.ktb.community.global.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptEncoder {

    private final BCryptPasswordEncoder encoder;

    public EncryptEncoder(final BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String bcryptEncrypt(String string) {
        return encoder.encode(string);
    }

    public boolean bcryptIsMatch(String string, String encodedString) {
        return encoder.matches(string, encodedString);
    }

    public String sha256Encrypt(String string) {
        return DigestUtils.sha256Hex(string);
    }

    public boolean sha256EncryptIsMatch(String string, String encodedString) {
        String encoded = sha256Encrypt(string);
        return encoded.equals(encodedString);
    }

}
