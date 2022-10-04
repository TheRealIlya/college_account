package by.academy.jee.web.facade.impl;

import by.academy.jee.exception.NotFoundException;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.service.PersonService;
import by.academy.jee.web.client.GroupFeignClient;
import by.academy.jee.web.dto.client.GroupClientResponseDto;
import by.academy.jee.web.dto.group.GroupDtoRequest;
import by.academy.jee.web.dto.group.GroupDtoResponse;
import by.academy.jee.web.facade.GroupFacade;
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

class GroupFacadeImplTest {

    private final GroupFeignClient groupFeignClient = Mockito.mock(GroupFeignClient.class);
    private final PersonService personService = Mockito.mock(PersonService.class);
    private final GroupDtoMapper groupDtoMapper = Mappers.getMapper(GroupDtoMapper.class);
    private final AdminDtoMapper adminDtoMapper = Mappers.getMapper(AdminDtoMapper.class);
    private final TeacherDtoMapper teacherDtoMapper = Mappers.getMapper(TeacherDtoMapper.class);
    private final StudentDtoMapper studentDtoMapper = Mappers.getMapper(StudentDtoMapper.class);
    private final PersonDtoMapper personDtoMapper = new PersonDtoMapper(
            adminDtoMapper, teacherDtoMapper, studentDtoMapper);
    private final GroupFacade groupFacade = new GroupFacadeImpl(
            groupFeignClient, personService, groupDtoMapper, personDtoMapper);

    @AfterEach
    void clearMocks() {
        Mockito.clearInvocations();
    }

    @Test
    void getAllGroupsTest() {
        GroupClientResponseDto group1 = new GroupClientResponseDto("g1", 1, List.of(2, 3), List.of(), List.of());
        List<GroupClientResponseDto> list = new ArrayList<>();
        list.add(group1);
        list.add(null);
        Mockito.when(groupFeignClient.getAllGroups())
                .thenReturn(list);

        Mockito.when(personService.getPersonById(Mockito.anyInt())).thenReturn(new Teacher());

        Mockito.when(personService.getPersonsByIdList(Mockito.anyList()))
                .thenReturn(List.of(new Student(), new Student()));

        assertThat(groupFacade.getAllGroups()).hasSize(2);
    }

    @Test
    void getGroupWhenTitleIsValidMustReturnGroup() {
        assertThat(mockGetGroupMethod("group")).isNotNull();
    }

    @Test
    void getGroupWhenTitleIsNotValidMustThrowRuntimeException() {
        assertThatThrownBy(() -> mockGetGroupMethod("test")).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createOrUpdateWhenEverythingIsValidMustNotThrowAnyException() {
        GroupDtoRequest groupDtoRequest = new GroupDtoRequest();
        groupDtoRequest.setStudentLogins(List.of("student"));
        groupDtoRequest.setTeacherLogin("teacher");
        groupDtoRequest.setThemeTitles(List.of("theme"));
        assertThat(mockCreateOrUpdateMethod(groupDtoRequest)).isNotNull();
    }

    @Test
    void createOrUpdateWhenTeacherLoginIsNullMustThrowNotFoundException() {
        GroupDtoRequest groupDtoRequest = new GroupDtoRequest();
        groupDtoRequest.setStudentLogins(List.of("student"));
        groupDtoRequest.setTeacherLogin(null);
        groupDtoRequest.setThemeTitles(List.of("theme"));
        assertThatThrownBy(() -> mockCreateOrUpdateMethod(groupDtoRequest)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteGroupByTitleWhenTitleIsValidMustNotThrowAnyException() {
        assertThat(mockDeleteGroupByTitleMethod("group")).isNotNull();
    }

    @Test
    void deleteGroupByTitleWhenTitleIsNotValidMustThrowRuntimeException() {
        assertThatThrownBy(() -> mockDeleteGroupByTitleMethod("gr")).isInstanceOf(RuntimeException.class);
    }

    private GroupDtoResponse mockGetGroupMethod(String title) {
        Mockito.when(groupFeignClient.getGroupByTitle(title)).then(invocation -> {
            if ("group".equals(title)) {
                return ResponseEntity.ok(new GroupClientResponseDto("group", 1, List.of(2), List.of(), List.of()));
            }
            throw new RuntimeException();
        });

        Mockito.when(personService.getPersonById(Mockito.anyInt())).thenReturn(new Teacher());

        Mockito.when(personService.getPersonsByIdList(Mockito.anyList()))
                .thenReturn(List.of(new Student()));

        return groupFacade.getGroup(title);
    }

    private GroupDtoResponse mockCreateOrUpdateMethod(GroupDtoRequest groupDtoRequest) {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        Mockito.when(personService.getPerson(groupDtoRequest.getTeacherLogin())).then(invocation -> {
            if (groupDtoRequest.getTeacherLogin() != null) {
                return teacher;
            }
            throw new NotFoundException();
        });

        Student student = new Student();
        student.setId(2);
        Mockito.when(personService.getPerson(groupDtoRequest.getStudentLogins().get(0))).then(invocation -> {
            if (groupDtoRequest.getStudentLogins().get(0) != null) {
                return student;
            }
            throw new NotFoundException();
        });

        Mockito.when(groupFeignClient.createOrUpdateGroup(Mockito.any()))
                .thenReturn(ResponseEntity.ok(new GroupClientResponseDto("group", 1, List.of(2), List.of(), List.of())));

        return groupFacade.createOrUpdateGroup(groupDtoRequest);
    }

    private GroupDtoResponse mockDeleteGroupByTitleMethod(String title) {
        Mockito.when(groupFeignClient.deleteGroupByTitle(title)).then(invocation -> {
            if ("group".equals(title)) {
                return ResponseEntity.ok(new GroupClientResponseDto("group", 1, List.of(2), List.of(), List.of()));
            }
            throw new RuntimeException();
        });

        return groupFacade.deleteGroupByTitle(title);
    }

}
