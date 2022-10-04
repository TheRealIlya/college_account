package by.academy.jee.web.facade.impl;

import by.academy.jee.exception.NotFoundException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.service.PersonService;
import by.academy.jee.web.client.GradeFeignClient;
import by.academy.jee.web.client.GroupFeignClient;
import by.academy.jee.web.dto.client.GroupClientResponseDto;
import by.academy.jee.web.dto.client.GroupClientResponseDtoWithId;
import by.academy.jee.web.dto.person.PersonDtoResponse;
import by.academy.jee.web.facade.PersonFacade;
import by.academy.jee.web.mapper.AdminDtoMapper;
import by.academy.jee.web.mapper.GroupDtoMapper;
import by.academy.jee.web.mapper.PersonDtoMapper;
import by.academy.jee.web.mapper.StudentDtoMapper;
import by.academy.jee.web.mapper.TeacherDtoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PersonFacadeImplTest {

    private final PersonService personService = Mockito.mock(PersonService.class);
    private final AdminDtoMapper adminDtoMapper = Mappers.getMapper(AdminDtoMapper.class);
    private final TeacherDtoMapper teacherDtoMapper = Mappers.getMapper(TeacherDtoMapper.class);
    private final StudentDtoMapper studentDtoMapper = Mappers.getMapper(StudentDtoMapper.class);
    private final PersonDtoMapper personDtoMapper = new PersonDtoMapper(
            adminDtoMapper, teacherDtoMapper, studentDtoMapper);
    private final GroupDtoMapper groupDtoMapper = Mappers.getMapper(GroupDtoMapper.class);
    private final GradeFeignClient gradeFeignClient = Mockito.mock(GradeFeignClient.class);
    private final GroupFeignClient groupFeignClient = Mockito.mock(GroupFeignClient.class);
    private final PersonFacade personFacade = new PersonFacadeImpl(
            personService, personDtoMapper, groupDtoMapper, gradeFeignClient, groupFeignClient);

    @AfterEach
    void clearMocks() {
        Mockito.clearInvocations();
    }

    @Test
    void getAllPersonsTest() {
        Mockito.when(personService.getAllPersons()).thenReturn(List.of(new Admin(), new Teacher(), new Student()));

        assertThat(personFacade.getAllPersons()).hasSize(3);
    }

    @Test
    void getPersonWhenLoginIsValidMustNotThrowAnyException() {
        assertThat(mockGetPersonMethod("valid")).isNotNull();
    }

    @Test
    void getPersonWhenLoginIsNotValidMustThrowNotFoundException() {
        assertThatThrownBy(() -> mockGetPersonMethod("va")).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deletePersonWhenPersonIsTeacherMustNotThrowAnyException() {
        assertThat(mockDeletePersonMethod("teacher")).isNotNull();
    }

    @Test
    void deletePersonWhenPersonIsAdminMustNotThrowAnyException() {
        assertThat(mockDeletePersonMethod("admin")).isNotNull();
    }

    @Test
    void deletePersonWhenPersonIsStudentMustNotThrowAnyException() {
        assertThat(mockDeletePersonMethod("student")).isNotNull();
    }

    @Test
    void deletePersonWhenLoginIsInvalidMustThrowNotFoundException() {
        assertThatThrownBy(() -> mockDeletePersonMethod("test")).isInstanceOf(NotFoundException.class);
    }

    private PersonDtoResponse mockGetPersonMethod(String login) {
        Mockito.when(personService.getPerson(login)).then(invocation -> {
            if ("valid".equals(login)) {
                return new Admin();
            }
            throw new NotFoundException();
        });

        return personFacade.getPerson(login);
    }

    private PersonDtoResponse mockDeletePersonMethod(String login) {
        Mockito.when(personService.getPerson(login)).then(invocation -> {
            switch (login) {
                case "admin":
                    return new Admin();
                case "teacher":
                    Teacher teacher = new Teacher();
                    teacher.setId(2);
                    return teacher;
                case "student":
                    Student student = new Student();
                    student.setId(1);
                    return student;
                default:
                    throw new NotFoundException();
            }
        });

        Mockito.when(gradeFeignClient.deleteGradesByStudentId(Mockito.anyInt())).thenReturn(ResponseEntity.ok("test"));

        GroupClientResponseDtoWithId group = new GroupClientResponseDtoWithId(
                "id", "title", 2, new ArrayList<>(List.of(1)), List.of(), List.of());
        Mockito.when(groupFeignClient.getGroupsForStudent(Mockito.anyInt())).thenReturn(List.of(group));

        Mockito.when(groupFeignClient.patchGroup(Mockito.any())).thenReturn(ResponseEntity.ok(
                new GroupClientResponseDto("title", 2, new ArrayList<>(List.of(1)), List.of(), List.of())));

        GroupClientResponseDtoWithId group2 = new GroupClientResponseDtoWithId(
                "id", "title", 4, new ArrayList<>(List.of(1)), List.of(), List.of());
        Mockito.when(groupFeignClient.getGroupsForTeacher(Mockito.anyInt())).thenReturn(List.of(group, group2));

        Mockito.when(personService.removePerson(Mockito.any())).thenReturn(new Admin());

        return personFacade.deletePerson(login);
    }

}
