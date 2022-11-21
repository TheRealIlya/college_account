package by.academy.jee.web.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GroupClientRequestDto {

    private String id;
    private String title;
    private Integer teacherId;
    private List<Integer> studentIds;
    private List<ThemeClientShortRequestDto> themes;

}
