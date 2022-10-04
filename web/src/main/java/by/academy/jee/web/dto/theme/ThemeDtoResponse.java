package by.academy.jee.web.dto.theme;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThemeDtoResponse {

    private String title;
    private List<String> groupTitles;

}
