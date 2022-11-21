package by.academy.jee.web.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ThemeClientResponseDto {

    private String title;
    private List<String> groupTitles;
    private List<String> gradeIds;

}
