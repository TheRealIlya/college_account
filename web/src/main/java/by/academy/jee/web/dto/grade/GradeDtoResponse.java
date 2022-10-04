package by.academy.jee.web.dto.grade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeDtoResponse {

    private int value;
    private int studentId;
    private String groupTitle;
    private String themeTitle;

}
