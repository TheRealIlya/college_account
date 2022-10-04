package by.academy.jee.web.dto.group;

import by.academy.jee.web.dto.person.PersonDtoResponse;
import by.academy.jee.web.dto.person.teacher.TeacherDtoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupDtoResponse {

    private String title;
    private TeacherDtoResponse teacher;
    private List<PersonDtoResponse> students;
    private List<String> themeTitles;
    private List<String> gradeIds;

}
