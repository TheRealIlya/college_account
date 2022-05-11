package by.academy.jee.web.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    public String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
