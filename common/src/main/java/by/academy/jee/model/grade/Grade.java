package by.academy.jee.model.grade;

import by.academy.jee.model.AbstractEntity;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.theme.Theme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "grade")
public class Grade extends AbstractEntity {

    private int value;
    private Student student;
    private Group group;
    private Theme theme;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}