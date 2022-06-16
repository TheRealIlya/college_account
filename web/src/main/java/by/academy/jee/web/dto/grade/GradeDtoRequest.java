package by.academy.jee.web.dto.grade;

import by.academy.jee.web.dto.group.GroupDtoRequest;
import by.academy.jee.web.dto.person.PersonDtoRequest;
import by.academy.jee.web.dto.theme.ThemeDtoRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GradeDtoRequest {

    private Integer id;
    @NotNull
    private int value;
    private PersonDtoRequest student;
    private GroupDtoRequest group;
    private ThemeDtoRequest theme;
}