package by.academy.jee.web.controller.rest;


import by.academy.jee.exception.NotFoundException;
import by.academy.jee.model.auth.UserPrincipal;
import by.academy.jee.model.auth.jwt.JwtRequest;
import by.academy.jee.model.person.Admin;
import by.academy.jee.service.auth.UserService;
import by.academy.jee.web.kafka.KafkaProducerService;
import by.academy.jee.web.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JwtAuthenticationControllerTest {

    private static final AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    private static final JwtTokenUtil jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
    private static final UserService userService = Mockito.mock(UserService.class);
    private static final KafkaProducerService kafkaProducerService = Mockito.mock(KafkaProducerService.class);
    private static MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new JwtAuthenticationController(
                        authenticationManager, jwtTokenUtil, userService, kafkaProducerService))
                .build();
    }

    @AfterEach
    void clearAllMocks() {
        Mockito.clearInvocations();
    }

    @Test
    @SneakyThrows
    void createAuthenticationTokenWhenCredentialsAreValidMustReturnToken() {
        JwtRequest jwtRequest = new JwtRequest("login", "password");
        mockForCreateAuthenticationTokenMethod(jwtRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("value"));
    }

    @Test
    @SneakyThrows
    void createAuthenticationTokenWhenCredentialsAreWrongMustThrowBadCredentialsException() {
        JwtRequest jwtRequest = new JwtRequest("login", "pass");
        assertThatThrownBy(() -> mockForCreateAuthenticationTokenMethod(jwtRequest))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(BadCredentialsException.class);
    }

    @SneakyThrows
    private ResultActions mockForCreateAuthenticationTokenMethod(JwtRequest jwtRequest) {
        Mockito.when(authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(jwtRequest.getLogin(), jwtRequest.getPassword())))
                .then(invocation -> {
                    if ("login".equals(jwtRequest.getLogin()) && "password".equals(jwtRequest.getPassword())) {
                        return new UsernamePasswordAuthenticationToken(jwtRequest.getLogin(), jwtRequest.getPassword());
                    }
                    throw new BadCredentialsException("message");
                });

        Mockito.when(userService.loadUserByUsername(jwtRequest.getLogin())).then(invocation -> {
            if ("login".equals(jwtRequest.getLogin())) {
                Admin admin = new Admin();
                admin.setLogin("login");
                return new UserPrincipal(admin);
            }
            throw new NotFoundException();
        });

        Mockito.doNothing().when(kafkaProducerService).logTokenGrant(Mockito.anyString());

        Mockito.when(jwtTokenUtil.generateToken(Mockito.any())).thenReturn("value");

        return mockMvc.perform(post("/rest/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)));
    }

}
