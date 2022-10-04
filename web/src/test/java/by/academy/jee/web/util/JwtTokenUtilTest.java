package by.academy.jee.web.util;

import by.academy.jee.model.auth.UserPrincipal;
import by.academy.jee.model.auth.jwt.JwtRequest;
import by.academy.jee.model.person.Admin;
import by.academy.jee.service.auth.UserService;
import by.academy.jee.web.controller.rest.JwtAuthenticationController;
import by.academy.jee.web.kafka.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class JwtTokenUtilTest {

    private static final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    private static final AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    private static final UserService userService = Mockito.mock(UserService.class);
    private static final KafkaProducerService kafkaProducerService = Mockito.mock(KafkaProducerService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String token;

    @AfterEach
    void clearMocks() {
        Mockito.clearInvocations();
    }

    @BeforeAll
    @SneakyThrows
    static void init() {
        String secret = "some_secret";
        long jwtValidity = 600000000L;
        Field secretField = JwtTokenUtil.class.getDeclaredField("secret");
        Field validityField = JwtTokenUtil.class.getDeclaredField("jwtValidity");
        secretField.setAccessible(true);
        validityField.setAccessible(true);
        secretField.set(jwtTokenUtil, secret);
        validityField.set(jwtTokenUtil, jwtValidity);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new JwtAuthenticationController(
                        authenticationManager, jwtTokenUtil, userService, kafkaProducerService))
                .build();

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new UsernamePasswordAuthenticationToken("Admin", "qwe"));

        Admin admin = new Admin();
        admin.setLogin("Admin");
        admin.setPassword("qwe");
        Mockito.when(userService.loadUserByUsername("Admin")).thenReturn(new UserPrincipal(admin));

        token = mockMvc.perform(post("/rest/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new JwtRequest("Admin", "qwe"))))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .split(":")[1]
                .substring(1)
                .split("\"")[0];
    }

    @Test
    void getUsernameFromTokenTest() {
        assertThat(jwtTokenUtil.getUsernameFromToken(token)).isEqualTo("Admin");
    }

    @Test
    void validateTokenWhenUsernamesAreSameMustReturnTrue() {
        Admin admin = new Admin();
        admin.setLogin("Admin");
        assertThat(jwtTokenUtil.validateToken(token, new UserPrincipal(admin))).isTrue();
    }

    @Test
    void validateTokenWhenUsernamesAreNotSameMustReturnFalse() {
        Admin admin = new Admin();
        admin.setLogin("Login");
        assertThat(jwtTokenUtil.validateToken(token, new UserPrincipal(admin))).isFalse();
    }

}
