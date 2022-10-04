package by.academy.jee.web.mapper;

import by.academy.jee.web.dto.client.GroupClientRequestDto;
import by.academy.jee.web.dto.client.GroupClientResponseDto;
import by.academy.jee.web.dto.client.GroupClientResponseDtoWithId;
import by.academy.jee.web.dto.client.ThemeClientShortRequestDto;
import by.academy.jee.web.dto.group.GroupDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupDtoMapper {

    default List<ThemeClientShortRequestDto> mapThemeTitlesToThemeShortDtos(List<String> themeTitles) {
        if (themeTitles == null) {
            return List.of();
        }
        return themeTitles.stream()
                .map(ThemeClientShortRequestDto::new)
                .collect(Collectors.toList());
    }

    GroupDtoResponse mapClientDtoToResponseDto(GroupClientResponseDto groupClientResponseDto);

    @Mapping(source = "themeTitles", target = "themes")
    GroupClientRequestDto mapClientResponseToClientRequest(GroupClientResponseDtoWithId groupClientResponseDto);

}
