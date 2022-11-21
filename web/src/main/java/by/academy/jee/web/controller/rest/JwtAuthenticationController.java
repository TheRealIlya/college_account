package by.academy.jee.web.controller.rest;

import by.academy.jee.model.auth.jwt.JwtRequest;
import by.academy.jee.model.auth.jwt.JwtResponse;
import by.academy.jee.service.auth.UserService;
import by.academy.jee.web.kafka.KafkaProducerService;
import by.academy.jee.web.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "auth workflow")
public class JwtAuthenticationController {

    private static final String TOKEN_GRANTED_PAYLOAD = "type:auth User \"%s\" successfully got token";
    private static final String INVALID_CREDENTIALS_PAYLOAD =
            "type:auth Attempt to get token with wrong credentials, user \"%s\"";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping(value = "/rest/authenticate")
    @Operation(summary = "Entry point to application, provides JWT")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        authenticate(jwtRequest.getLogin(), jwtRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getLogin());
        String tokenGrantedLog = String.format(TOKEN_GRANTED_PAYLOAD, userDetails.getUsername());
        kafkaProducerService.logTokenGrant(tokenGrantedLog);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException exception) {
            String attemptToGetTokenLog = String.format(INVALID_CREDENTIALS_PAYLOAD, username);
            kafkaProducerService.logTokenGrant(attemptToGetTokenLog);
            throw exception;
        }
    }

}
