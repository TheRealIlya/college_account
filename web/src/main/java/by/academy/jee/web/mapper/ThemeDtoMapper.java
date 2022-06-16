package by.academy.jee.web.mapper;

import by.academy.jee.web.dto.theme.ThemeDtoRequest;
import by.academy.jee.web.dto.theme.ThemeDtoResponse;
import by.academy.jee.model.theme.Theme;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeDtoMapper extends GenericMapper<ThemeDtoRequest, ThemeDtoResponse, Theme> {
}