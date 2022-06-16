package by.academy.jee.web.controller.rest;


import by.academy.jee.web.dto.theme.ThemeDtoRequest;
import by.academy.jee.web.dto.theme.ThemeDtoResponse;
import by.academy.jee.web.mapper.ThemeDtoMapper;
import by.academy.jee.model.theme.Theme;
import by.academy.jee.service.facade.CollegeFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/rest/themes")
@SecurityRequirement(name = "jwtAuth")
public class ThemeJsonController {

    private final CollegeFacade collegeFacade;
    private final ThemeDtoMapper themeDtoMapper;

    @GetMapping
    public List<ThemeDtoResponse> getAllThemes() {
        return themeDtoMapper.mapModelListToDtoList(collegeFacade.getAllThemes());
    }

    @GetMapping(value = "/{title}")
    public ResponseEntity<ThemeDtoResponse> getTheme(@PathVariable @NotNull String title) {
        return ResponseEntity.ok(themeDtoMapper.mapModelToDto(collegeFacade.getTheme(title)));
    }

    @PostMapping
    public ResponseEntity<ThemeDtoResponse> createTheme(@Valid @RequestBody ThemeDtoRequest themeDtoRequest) {
        Theme theme = themeDtoMapper.mapDtoToModel(themeDtoRequest);
        return ResponseEntity.ok(themeDtoMapper.mapModelToDto(collegeFacade.createTheme(theme)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ThemeDtoResponse> updateTheme(@Valid @RequestBody ThemeDtoRequest themeDtoRequest,
                                                        @PathVariable @Min(1) int id) {
        Theme theme = themeDtoMapper.mapDtoToModel(themeDtoRequest);
        return ResponseEntity.ok(themeDtoMapper.mapModelToDto(collegeFacade.updateTheme(theme, id)));
    }

    @DeleteMapping(value = "/{title}")
    public ResponseEntity<ThemeDtoResponse> deleteTheme(@PathVariable @NotNull String title) {
        Theme theme = collegeFacade.removeTheme(title);
        return ResponseEntity.ok(themeDtoMapper.mapModelToDto(theme));
    }
}