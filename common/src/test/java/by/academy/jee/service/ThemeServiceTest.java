package by.academy.jee.service;

import by.academy.jee.dao.ThemeDao;
import by.academy.jee.exception.NotFoundException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.theme.Theme;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ThemeServiceTest {

    private final ThemeDao themeDao = mock(ThemeDao.class);
    private final ThemeService themeService = new ThemeService(themeDao);

    @AfterEach
    void clearMocks() {
        clearInvocations();
    }

    @Test
    void getThemeWhenTitleIsValid() {
        Theme expected = new Theme();
        expected.setTitle("Valid theme title");
        Theme actual = getTheme(expected.getTitle());
        assertEquals(expected, actual);
    }

    @Test
    void getThemeWhenTitleIsNotValid() {
        assertThrows(NotFoundException.class, () -> getTheme("Wrong theme title"));
    }

    @Test
    void updateThemeWhenAllConditionsValid() {
        Theme expected = new Theme();
        expected.setId(1);
        Theme actual = updateTheme(expected, 1);
        assertEquals(expected, actual);
    }

    @Test
    void updateThemeWhenIdIsNotEqualToThemeId() {
        Theme theme = new Theme();
        theme.setId(1);
        assertThrows(ServiceException.class, () -> updateTheme(theme, 2));
    }

    @Test
    void updateThemeWhenThemeIsNull() {
        assertThrows(ServiceException.class, () -> themeService.updateTheme(null, 3));
    }

    private Theme getTheme(String title) {
        String validTitle = "Valid theme title";
        when(themeDao.findByTitle(title)).then(invocation -> {
            if (validTitle.equals(title)) {
                Theme theme = new Theme();
                theme.setTitle(title);
                return Optional.of(theme);
            }
            return Optional.empty();
        });
        return themeService.getTheme(title);
    }

    private Theme updateTheme(Theme theme, int id) {
        when(themeDao.save(theme)).thenReturn(theme);
        return themeService.updateTheme(theme, id);
    }
}