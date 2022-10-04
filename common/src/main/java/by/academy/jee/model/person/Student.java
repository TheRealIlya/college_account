package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("2")
public class Student extends Person {

    public Student() {
        setRole(Role.ROLE_STUDENT);
    }

}
