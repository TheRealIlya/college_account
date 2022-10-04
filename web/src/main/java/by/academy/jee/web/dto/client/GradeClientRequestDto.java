package by.academy.jee.web.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class GradeClientRequestDto {

    private String id;
    private int value;

    @Setter
    private int studentId;
    private GroupClientShortRequestDto group;
    private ThemeClientShortRequestDto theme;

}
