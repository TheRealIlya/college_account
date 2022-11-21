package by.academy.jee.web.facade.impl;

import by.academy.jee.model.AbstractEntity;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.service.PersonService;
import by.academy.jee.web.client.GroupFeignClient;
import by.academy.jee.web.dto.client.GroupClientRequestDto;
import by.academy.jee.web.dto.client.GroupClientResponseDto;
import by.academy.jee.web.dto.client.ThemeClientShortRequestDto;
import by.academy.jee.web.dto.group.GroupDtoRequest;
import by.academy.jee.web.dto.group.GroupDtoResponse;
import by.academy.jee.web.dto.person.teacher.TeacherDtoResponse;
import by.academy.jee.web.facade.GroupFacade;
import by.academy.jee.web.mapper.GroupDtoMapper;
import by.academy.jee.web.mapper.PersonDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupFacadeImpl implements GroupFacade {

    private final GroupFeignClient groupFeignClient;
    private final PersonService personService;
    private final GroupDtoMapper groupDtoMapper;
    private final PersonDtoMapper personDtoMapper;

    @Override
    public List<GroupDtoResponse> getAllGroups() {
        List<GroupClientResponseDto> groups = groupFeignClient.getAllGroups();
        return mapClientGroupListToResponse(groups);
    }

    @Override
    public GroupDtoResponse getGroup(String title) {
        GroupClientResponseDto group = groupFeignClient.getGroupByTitle(title).getBody();
        return mapClientGroupToResponse(group);
    }

    @Override
    public GroupDtoResponse createOrUpdateGroup(GroupDtoRequest groupDtoRequest) {
        GroupClientRequestDto group = mapRequestGroupToClientDto(groupDtoRequest);
        return groupDtoMapper.mapClientDtoToResponseDto(groupFeignClient.createOrUpdateGroup(group).getBody());
    }

    @Override
    public GroupDtoResponse deleteGroupByTitle(String title) {
        GroupClientResponseDto group = groupFeignClient.deleteGroupByTitle(title).getBody();
        return groupDtoMapper.mapClientDtoToResponseDto(group);
    }

    private List<GroupDtoResponse> mapClientGroupListToResponse(List<GroupClientResponseDto> groups) {
        List<GroupDtoResponse> groupsForResponse = new ArrayList<>();
        for (GroupClientResponseDto groupFromClient : groups) {
            groupsForResponse.add(mapClientGroupToResponse(groupFromClient));
        }
        return groupsForResponse;
    }

    private GroupDtoResponse mapClientGroupToResponse(GroupClientResponseDto groupFromClient) {
        if (groupFromClient == null) {
            return null;
        }
        Person person = personService.getPersonById(groupFromClient.getTeacherId());
        Teacher teacher = (Teacher) person;
        List<Person> persons = personService.getPersonsByIdList(groupFromClient.getStudentIds());
        GroupDtoResponse groupDtoResponse = groupDtoMapper.mapClientDtoToResponseDto(groupFromClient);
        groupDtoResponse.setTeacher((TeacherDtoResponse) personDtoMapper.mapModelToDto(teacher));
        groupDtoResponse.setStudents(personDtoMapper.mapModelListToDtoList(persons));
        return groupDtoResponse;
    }

    private GroupClientRequestDto mapRequestGroupToClientDto(GroupDtoRequest groupDtoRequest) {
        Integer teacherId = ((Teacher) personService.getPerson(groupDtoRequest.getTeacherLogin())).getId();
        List<Integer> studentIds = getStudentIdsFromStudentLogins(groupDtoRequest.getStudentLogins());
        List<ThemeClientShortRequestDto> themes = mapThemeTitlesToShortDtos(groupDtoRequest.getThemeTitles());
        return new GroupClientRequestDto(groupDtoRequest.getId(), groupDtoRequest.getTitle(), teacherId, studentIds,
                themes);
    }

    private List<Integer> getStudentIdsFromStudentLogins(List<String> studentLogins) {
        return studentLogins.stream()
                .map(personService::getPerson)
                .map(Student.class::cast)
                .map(AbstractEntity::getId)
                .collect(Collectors.toList());
    }

    private List<ThemeClientShortRequestDto> mapThemeTitlesToShortDtos(List<String> titles) {
        return titles.stream()
                .map(ThemeClientShortRequestDto::new)
                .collect(Collectors.toList());
    }

}
