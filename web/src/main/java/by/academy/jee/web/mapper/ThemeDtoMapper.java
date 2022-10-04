package by.academy.jee.web.mapper;

import by.academy.jee.web.dto.client.GroupClientRequestDto;
import by.academy.jee.web.dto.client.ThemeClientRequestDto;
import by.academy.jee.web.dto.client.ThemeClientResponseDto;
import by.academy.jee.web.dto.theme.ThemeDtoRequest;
import by.academy.jee.web.dto.theme.ThemeDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ThemeDtoMapper {

    default List<GroupClientRequestDto> mapTitlesToGroups(List<String> titles) {
        return titles.stream()
                .map(title -> new GroupClientRequestDto(null, title, null, null, null))
                .collect(Collectors.toList());
    }

    ThemeDtoResponse mapClientDtoToResponseDto(ThemeClientResponseDto themeClientResponseDto);

    List<ThemeDtoResponse> mapClientDtoListToResponseDtoList(List<ThemeClientResponseDto> themeClientResponseDtoList);

    @Mapping(source = "groupTitles", target = "groups")
    ThemeClientRequestDto mapRequestDtoToClientDto(ThemeDtoRequest themeDtoRequest);

}
