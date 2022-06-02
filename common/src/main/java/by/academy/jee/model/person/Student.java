package by.academy.jee.model.person;

import by.academy.jee.model.grade.Grade;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.role.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Student extends Person {

    private List<Grade> grades;
    private List<Group> groups;

    public Student() {
        setRole(Role.ROLE_STUDENT);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}