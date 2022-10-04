package by.academy.jee.web.facade.impl;

import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.service.PersonService;
import by.academy.jee.web.client.GradeFeignClient;
import by.academy.jee.web.client.GroupFeignClient;
import by.academy.jee.web.dto.client.GroupClientResponseDtoWithId;
import by.academy.jee.web.dto.person.PersonDtoRequest;
import by.academy.jee.web.dto.person.PersonDtoResponse;
import by.academy.jee.web.facade.PersonFacade;
import by.academy.jee.web.mapper.GroupDtoMapper;
import by.academy.jee.web.mapper.PersonDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonFacadeImpl implements PersonFacade {

    private final PersonService personService;
    private final PersonDtoMapper personDtoMapper;
    private final GroupDtoMapper groupDtoMapper;
    private final GradeFeignClient gradeFeignClient;
    private final GroupFeignClient groupFeignClient;

    @Override
    public List<PersonDtoResponse> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return personDtoMapper.mapModelListToDtoList(persons);
    }

    @Override
    public PersonDtoResponse getPerson(String login) {
        return personDtoMapper.mapModelToDto(personService.getPerson(login));
    }

    @Override
    public PersonDtoResponse createPerson(PersonDtoRequest personDtoRequest) {
        Person person = personDtoMapper.mapDtoToModel(personDtoRequest);
        return personDtoMapper.mapModelToDto(personService.createPerson(person));
    }

    @Override
    public PersonDtoResponse updatePerson(PersonDtoRequest personDtoRequest, int id) {
        Person person = personDtoMapper.mapDtoToModel(personDtoRequest);
        return personDtoMapper.mapModelToDto(personService.updatePerson(person, id));
    }

    @Override
    public PersonDtoResponse deletePerson(String login) {
        Person person = personService.getPerson(login);
        deleteConnectedEntitiesForStudent(person);
        deleteConnectedEntitiesForTeacher(person);
        return personDtoMapper.mapModelToDto(personService.removePerson(person));
    }

    private void deleteConnectedEntitiesForTeacher(Person person) {
        if (person instanceof Teacher) {
            removeTeacherFromGroups((Teacher) person);
        }
    }

    private void deleteConnectedEntitiesForStudent(Person person) {
        if (person instanceof Student) {
            gradeFeignClient.deleteGradesByStudentId(person.getId());
            removeStudentFromGroups((Student) person);
        }
    }

    private void removeStudentFromGroups(Student student) {
        int studentId = student.getId();
        List<GroupClientResponseDtoWithId> groups = groupFeignClient.getGroupsForStudent(studentId);
        groups.stream()
                .filter(group -> group.getStudentIds().contains(studentId))
                .peek(group -> group.getStudentIds().remove(Integer.valueOf(studentId)))
                .forEach(group -> groupFeignClient.patchGroup(
                        groupDtoMapper.mapClientResponseToClientRequest(group)));
    }

    private void removeTeacherFromGroups(Teacher teacher) {
        int teacherId = teacher.getId();
        List<GroupClientResponseDtoWithId> groups = groupFeignClient.getGroupsForTeacher(teacherId);
        groups.stream()
                .filter(group -> group.getTeacherId() == teacherId)
                .peek(group -> group.setTeacherId(null))
                .forEach(group -> groupFeignClient.patchGroup(
                        groupDtoMapper.mapClientResponseToClientRequest(group)));
    }

}
