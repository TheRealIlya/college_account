package by.academy.jee.web.dto.person;

import by.academy.jee.model.person.role.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDtoResponse {

    private Integer id;
    private String login;
    private String name;
    private Role role;

}
