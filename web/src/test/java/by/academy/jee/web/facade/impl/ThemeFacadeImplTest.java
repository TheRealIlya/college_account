package by.academy.jee.web.facade.impl;

import by.academy.jee.web.client.ThemeFeignClient;
import by.academy.jee.web.dto.client.ThemeClientResponseDto;
import by.academy.jee.web.dto.theme.ThemeDtoResponse;
import by.academy.jee.web.facade.ThemeFacade;
import by.academy.jee.web.mapper.ThemeDtoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThemeFacadeImplTest {

    private final ThemeFeignClient themeFeignClient = Mockito.mock(ThemeFeignClient.class);
    private final ThemeDtoMapper themeDtoMapper = Mappers.getMapper(ThemeDtoMapper.class);
    private final ThemeFacade themeFacade = new ThemeFacadeImpl(themeFeignClient, themeDtoMapper);

    @AfterEach
    void clearMocks() {
        Mockito.clearInvocations();
    }

    @Test
    void getAllThemesTest() {
        ThemeClientResponseDto theme = new ThemeClientResponseDto("title", List.of(), List.of());
        List<ThemeClientResponseDto> themes = new ArrayList<>();
        themes.add(theme);
        themes.add(null);
        Mockito.when(themeFeignClient.getAllThemes()).thenReturn(themes);

        assertThat(themeFacade.getAllThemes()).hasSize(2);
    }

    @Test
    void getThemeByTitleWhenTitleIsValidMustNotThrowAnyException() {
        assertThat(mockGetThemeByTitleMethod("valid")).isNotNull();
    }

    @Test
    void getThemeByTitleWhenTitleIsNotValidMustThrowRuntimeException() {
        assertThatThrownBy(() -> mockGetThemeByTitleMethod("test")).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteThemeByTitleWhenTitleIsValidMustNotThrowAnyException() {
        assertThat(mockDeleteThemeByTitleMethod("valid")).isNotNull();
    }

    @Test
    void deleteThemeByTitleWhenTitleIsNotValidMustThrowRuntimeException() {
        assertThatThrownBy(() -> mockDeleteThemeByTitleMethod("test")).isInstanceOf(RuntimeException.class);
    }

    private ThemeDtoResponse mockGetThemeByTitleMethod(String title) {
        Mockito.when(themeFeignClient.getThemeByTitle(title)).then(invocation -> {
           if ("valid".equals(title)) {
               return ResponseEntity.ok(new ThemeClientResponseDto("valid", List.of(), List.of()));
           }
           throw new RuntimeException();
        });

        return themeFacade.getThemeByTitle(title);
    }

    private ThemeDtoResponse mockDeleteThemeByTitleMethod(String title) {
        Mockito.when(themeFeignClient.deleteThemeByTitle(title)).then(invocation -> {
            if ("valid".equals(title)) {
                return ResponseEntity.ok(new ThemeClientResponseDto("valid", List.of(), List.of()));
            }
            throw new RuntimeException();
        });

        return themeFacade.deleteThemeByTitle(title);
    }

}
