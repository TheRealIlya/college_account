package by.academy.jee.web.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemeClientRequestDto {

    private String id;
    private String title;
    private List<GroupClientRequestDto> groups;
    private List<GradeClientRequestDto> grades;

}
