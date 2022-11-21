package by.academy.jee.web.dto.theme;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ThemeDtoRequest {

    private String id;

    @NotNull
    private String title;
    private List<String> groupTitles;

}
