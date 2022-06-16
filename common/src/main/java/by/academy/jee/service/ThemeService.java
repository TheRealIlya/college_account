package by.academy.jee.service;

import by.academy.jee.dao.ThemeDao;
import by.academy.jee.exception.NotFoundException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.theme.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private static final String BAD_REQUEST_UPDATE_THEME = "Bad request - theme is null or id not equals to theme.id";

    private final ThemeDao themeDao;

    public List<Theme> getAllThemes() {
        return themeDao.findAll();
    }

    public Theme getTheme(String title) {
        return themeDao.findByTitle(title).orElseThrow(NotFoundException::new);
    }

    public Theme createTheme(Theme theme) {
        return themeDao.save(theme);
    }

    public Theme updateTheme(Theme newTheme, int id) {
        if (newTheme != null && newTheme.getId() == id) {
            return themeDao.save(newTheme);
        }
        throw new ServiceException(BAD_REQUEST_UPDATE_THEME);
    }

    public Theme removeTheme(Theme theme) {
        themeDao.delete(theme);
        return theme;
    }
}