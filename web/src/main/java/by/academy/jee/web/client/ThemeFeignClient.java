package by.academy.jee.web.client;

import by.academy.jee.web.dto.client.ThemeClientRequestDto;
import by.academy.jee.web.dto.client.ThemeClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "theme-service", url = "http://college-core-service:8060/themes")
public interface ThemeFeignClient {

    @GetMapping
    List<ThemeClientResponseDto> getAllThemes();

    @GetMapping(value = "/{title}")
    ResponseEntity<ThemeClientResponseDto> getThemeByTitle(@PathVariable String title);

    @PostMapping
    ResponseEntity<ThemeClientResponseDto> createOrUpdateTheme(
            @RequestBody ThemeClientRequestDto themeClientRequestDto);

    @DeleteMapping(value = "/{title}")
    ResponseEntity<ThemeClientResponseDto> deleteThemeByTitle(@PathVariable String title);

}
