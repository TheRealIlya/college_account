package by.academy.jee.web.facade.impl;

import by.academy.jee.web.client.ThemeFeignClient;
import by.academy.jee.web.dto.client.ThemeClientResponseDto;
import by.academy.jee.web.dto.theme.ThemeDtoRequest;
import by.academy.jee.web.dto.theme.ThemeDtoResponse;
import by.academy.jee.web.facade.ThemeFacade;
import by.academy.jee.web.mapper.ThemeDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ThemeFacadeImpl implements ThemeFacade {

    private final ThemeFeignClient themeFeignClient;
    private final ThemeDtoMapper themeDtoMapper;

    @Override
    public List<ThemeDtoResponse> getAllThemes() {
        List<ThemeClientResponseDto> themes = themeFeignClient.getAllThemes();
        return themeDtoMapper.mapClientDtoListToResponseDtoList(themes);
    }

    @Override
    public ThemeDtoResponse getThemeByTitle(String title) {
        ThemeClientResponseDto theme = themeFeignClient.getThemeByTitle(title).getBody();
        return themeDtoMapper.mapClientDtoToResponseDto(theme);
    }

    @Override
    public ThemeDtoResponse createOrUpdateTheme(ThemeDtoRequest themeDtoRequest) {
        ThemeClientResponseDto theme = themeFeignClient.createOrUpdateTheme(
                themeDtoMapper.mapRequestDtoToClientDto(themeDtoRequest)).getBody();
        return themeDtoMapper.mapClientDtoToResponseDto(theme);
    }

    @Override
    public ThemeDtoResponse deleteThemeByTitle(String title) {
        ThemeClientResponseDto theme = themeFeignClient.deleteThemeByTitle(title).getBody();
        return themeDtoMapper.mapClientDtoToResponseDto(theme);
    }

}
