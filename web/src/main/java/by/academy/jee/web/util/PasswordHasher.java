package by.academy.jee.web.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class PasswordHasher {

    private final Base64.Encoder passwordEncoder = Base64.getEncoder();

    public String getEncryptedPassword(String password) {
        return passwordEncoder.encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
}