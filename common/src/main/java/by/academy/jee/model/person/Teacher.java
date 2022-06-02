package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Teacher extends Person {

    private Map<Integer, Double> salaries = new HashMap<>();

    public Teacher() {
        setRole(Role.ROLE_TEACHER);
    }

    public void setSalaries(Map<Integer, Double> salaries) {
        this.salaries = salaries;
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