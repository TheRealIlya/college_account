package by.academy.jee.web.dto.group;

import by.academy.jee.web.dto.person.teacher.TeacherDtoResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDtoResponse {

    private Integer id;
    private String title;
    private TeacherDtoResponse teacher;
}