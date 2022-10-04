package by.academy.jee.web.controller.rest;

import by.academy.jee.web.dto.grade.GradeDtoRequest;
import by.academy.jee.web.dto.grade.GradeDtoResponse;
import by.academy.jee.web.facade.GradeFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GradeJsonControllerTest {

    private static final GradeFacade gradeFacade = Mockito.mock(GradeFacade.class);
    private static MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new GradeJsonController(gradeFacade))
                .build();
    }

    @AfterEach
    void clearAllMocks() {
        Mockito.clearInvocations();
    }

    @Test
    @SneakyThrows
    void getAllGradesTest() {
        Mockito.when(gradeFacade.getAllGrades()).thenReturn(List.of(new GradeDtoResponse(), new GradeDtoResponse()));
        mockMvc.perform(get("/rest/grades")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void createGradeWhenEverythingIsValidMustReturnStatus200() {
        GradeDtoRequest gradeDtoRequest = new GradeDtoRequest();
        gradeDtoRequest.setValue(4);
        mockForCreateGradeMethod(gradeDtoRequest)
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void createGradeWhenValueIsNotValidMustReturnStatus400() {
        mockForCreateGradeMethod(new GradeDtoRequest())
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    private ResultActions mockForCreateGradeMethod(GradeDtoRequest gradeDtoRequest) {
        Mockito.when(gradeFacade.createOrUpdateGrade(Mockito.any()))
                .thenReturn(new GradeDtoResponse());
        return mockMvc.perform(post("/rest/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gradeDtoRequest)));
    }

}
