package by.academy.jee.model.person;

import by.academy.jee.model.AbstractEntity;
import by.academy.jee.model.person.role.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "person")
public abstract class Person extends AbstractEntity {

    @Indexed(unique = true)
    private String login;
    private String password;
    private String name;
    private int age;
    private Role role;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}