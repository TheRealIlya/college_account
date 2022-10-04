package by.academy.jee.web.facade;

import by.academy.jee.web.dto.theme.ThemeDtoRequest;
import by.academy.jee.web.dto.theme.ThemeDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThemeFacade {

    List<ThemeDtoResponse> getAllThemes();

    ThemeDtoResponse getThemeByTitle(String title);

    ThemeDtoResponse createOrUpdateTheme(ThemeDtoRequest themeDtoRequest);

    ThemeDtoResponse deleteThemeByTitle(String title);

}
