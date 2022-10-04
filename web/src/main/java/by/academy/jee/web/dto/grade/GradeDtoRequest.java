package by.academy.jee.web.dto.grade;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class GradeDtoRequest {

    private String id;

    @Min(1)
    private int value;
    private String studentLogin;
    private String groupTitle;
    private String themeTitle;

}
