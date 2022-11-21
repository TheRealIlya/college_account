package by.academy.jee.web.dto.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class GroupDtoRequest {

    private String id;

    @NotNull
    private String title;
    private String teacherLogin;
    private List<String> studentLogins;
    private List<String> themeTitles;

}
