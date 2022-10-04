package by.academy.jee.web.controller.rest;


import by.academy.jee.web.dto.theme.ThemeDtoRequest;
import by.academy.jee.web.dto.theme.ThemeDtoResponse;
import by.academy.jee.web.facade.ThemeFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/rest/themes")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "ThemeController", description = "CRUD for themes")
public class ThemeJsonController {

    private final ThemeFacade themeFacade;

    @GetMapping
    public List<ThemeDtoResponse> getAllThemes() {
        return themeFacade.getAllThemes();
    }

    @GetMapping(value = "/{title}")
    @Operation(summary = "Get theme by title")
    public ResponseEntity<ThemeDtoResponse> getTheme(@PathVariable @NotNull String title) {
        return ResponseEntity.ok(themeFacade.getThemeByTitle(title));
    }

    @PostMapping
    @Operation(summary = "Create theme",
            description = "Accept theme in request body, cause 400 if input is invalid or title is already exist")
    public ResponseEntity<ThemeDtoResponse> createTheme(@Valid @RequestBody ThemeDtoRequest themeDtoRequest) {
        return ResponseEntity.ok(themeFacade.createOrUpdateTheme(themeDtoRequest));
    }

    @DeleteMapping(value = "/{title}")
    @Operation(summary = "Delete theme", description = "Includes deletion of theme's grades")
    public ResponseEntity<ThemeDtoResponse> deleteTheme(@PathVariable @NotNull String title) {
        return ResponseEntity.ok(themeFacade.deleteThemeByTitle(title));
    }

}
