package by.academy.jee.web.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GradeClientResponseDto {

    private String id;
    private int value;
    private int studentId;
    private String groupTitle;
    private String themeTitle;

}
