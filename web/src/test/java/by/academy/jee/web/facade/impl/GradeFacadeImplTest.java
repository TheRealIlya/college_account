package by.academy.jee.web.facade.impl;

import by.academy.jee.exception.NotFoundException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Student;
import by.academy.jee.service.PersonService;
import by.academy.jee.web.client.GradeFeignClient;
import by.academy.jee.web.dto.client.GradeClientResponseDto;
import by.academy.jee.web.dto.grade.GradeDtoRequest;
import by.academy.jee.web.dto.grade.GradeDtoResponse;
import by.academy.jee.web.facade.GradeFacade;
import by.academy.jee.web.mapper.GradeDtoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GradeFacadeImplTest {

    private final GradeFeignClient gradeFeignClient = Mockito.mock(GradeFeignClient.class);
    private final GradeDtoMapper gradeDtoMapper = Mappers.getMapper(GradeDtoMapper.class);
    private final PersonService personService = Mockito.mock(PersonService.class);
    private final GradeFacade gradeFacade = new GradeFacadeImpl(gradeFeignClient, gradeDtoMapper, personService);

    @AfterEach
    void clearMocks() {
        Mockito.clearInvocations();
    }

    @Test
    void getAllGradesTest() {
        Mockito.when(gradeFeignClient.getAllGrades())
                .thenReturn(List.of(new GradeClientResponseDto("id", 1, 2, "group", "theme")));
        assertThat(gradeFacade.getAllGrades())
                .isNotEmpty()
                .hasSize(1)
                .hasOnlyElementsOfType(GradeDtoResponse.class);
    }

    @Test
    void createOrUpdateGradeWhereEverythingIsValidMustNotThrowAnyException() {
        GradeDtoRequest gradeDtoRequest = new GradeDtoRequest();
        gradeDtoRequest.setGroupTitle("group");
        gradeDtoRequest.setThemeTitle("theme");
        gradeDtoRequest.setValue(1);
        gradeDtoRequest.setStudentLogin("student");
        assertThat(mockCreateOrUpdateMethod(gradeDtoRequest)).isNotNull();
    }

    @Test
    void createOrUpdateGradeWherePersonIsNotStudentMustThrowServiceException() {
        GradeDtoRequest gradeDtoRequest = new GradeDtoRequest();
        gradeDtoRequest.setGroupTitle("group");
        gradeDtoRequest.setThemeTitle("theme");
        gradeDtoRequest.setValue(1);
        gradeDtoRequest.setStudentLogin("admin");
        assertThatThrownBy(() -> mockCreateOrUpdateMethod(gradeDtoRequest)).isInstanceOf(ServiceException.class);
    }

    @Test
    void createOrUpdateGradeWhereStudentLoginIsNullMustThrowNotFoundException() {
        GradeDtoRequest gradeDtoRequest = new GradeDtoRequest();
        gradeDtoRequest.setGroupTitle("group");
        gradeDtoRequest.setThemeTitle("theme");
        gradeDtoRequest.setValue(1);
        gradeDtoRequest.setStudentLogin(null);
        assertThatThrownBy(() -> mockCreateOrUpdateMethod(gradeDtoRequest)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void createOrUpdateGradeWhereGroupTitleIsNotValidMustThrowServiceException() {
        GradeDtoRequest gradeDtoRequest = new GradeDtoRequest();
        gradeDtoRequest.setGroupTitle("gr");
        gradeDtoRequest.setThemeTitle("theme");
        gradeDtoRequest.setValue(1);
        gradeDtoRequest.setStudentLogin("student");
        assertThatThrownBy(() -> mockCreateOrUpdateMethod(gradeDtoRequest)).isInstanceOf(ServiceException.class);
    }

    private GradeDtoResponse mockCreateOrUpdateMethod(GradeDtoRequest gradeDtoRequest) {
        Mockito.when(personService.getPerson(gradeDtoRequest.getStudentLogin())).then(invocation -> {
            if ("student".equals(gradeDtoRequest.getStudentLogin())) {
                Student student = new Student();
                student.setId(2);
                return student;
            }
            if (gradeDtoRequest.getStudentLogin() != null) {
                return new Admin();
            }
            throw new NotFoundException();
        });

        Mockito.when(gradeFeignClient.createOrUpdateGrade(Mockito.any())).then(invocation -> {
            boolean groupIsValid = "group".equals(gradeDtoRequest.getGroupTitle());
            boolean themeIsValid = "theme".equals(gradeDtoRequest.getThemeTitle());
            if (groupIsValid && themeIsValid) {
                return ResponseEntity.ok(new GradeClientResponseDto("id", 1, 2, "group", "theme"));
            }
            throw new ServiceException("message");
        });

        return gradeFacade.createOrUpdateGrade(gradeDtoRequest);
    }

}
