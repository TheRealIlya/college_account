package by.academy.jee.service.facade;

import by.academy.jee.model.grade.Grade;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.theme.Theme;
import by.academy.jee.service.GradeService;
import by.academy.jee.service.GroupService;
import by.academy.jee.service.PersonService;
import by.academy.jee.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CollegeFacade {

    private final PersonService personService;
    private final GradeService gradeService;
    private final GroupService groupService;
    private final ThemeService themeService;

    public Person createPerson(Person person) {
        return personService.createPerson(person);
    }

    public Person getPerson(String login) {
        return personService.getPerson(login);
    }

    public Person updatePerson(Person newPerson, int id) {
        return personService.updatePerson(newPerson, id);
    }

    public Person removePerson(Person person) {
        return personService.removePerson(person);
    }

    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    public Grade createGrade(Grade grade) {
        return gradeService.createGrade(grade);
    }

    public Grade updateGrade(Grade grade, int id) {
        return gradeService.updateGrade(grade, id);
    }

    public Grade removeGrade(int id) {
        return gradeService.removeGrade(id);
    }

    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    public Group getGroup(String title) {
        return groupService.getGroup(title);
    }

    public Group createGroup(Group group) {
        return groupService.createGroup(group);
    }

    public Group updateGroup(Group newGroup, int id) {
        return groupService.updateGroup(newGroup, id);
    }

    public Group removeGroupByTitle(String title) {
        Group group = groupService.getGroup(title);
        group.getGrades().stream()
                .map(Grade::getId)
                .forEach(gradeService::removeGrade);
        group.getStudents().stream()
                .map(Student::getGroups)
                .forEach(groups -> groups.remove(group));
        return groupService.removeGroup(group);
    }

    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    public Theme getTheme(String title) {
        return themeService.getTheme(title);
    }

    public Theme createTheme(Theme theme) {
        return themeService.createTheme(theme);
    }

    public Theme updateTheme(Theme newTheme, int id) {
        return themeService.updateTheme(newTheme, id);
    }

    public Theme removeTheme(String title) {
        Theme theme = themeService.getTheme(title);
        theme.getGrades().stream()
                .map(Grade::getId)
                .forEach(gradeService::removeGrade);
        theme.getGroups().stream()
                .map(Group::getThemes)
                .forEach(themes -> themes.remove(theme));
        return themeService.removeTheme(theme);
    }
}