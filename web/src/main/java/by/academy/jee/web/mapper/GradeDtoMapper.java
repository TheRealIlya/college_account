package by.academy.jee.web.mapper;

import by.academy.jee.web.dto.client.GradeClientRequestDto;
import by.academy.jee.web.dto.client.GradeClientResponseDto;
import by.academy.jee.web.dto.client.GroupClientShortRequestDto;
import by.academy.jee.web.dto.client.ThemeClientShortRequestDto;
import by.academy.jee.web.dto.grade.GradeDtoRequest;
import by.academy.jee.web.dto.grade.GradeDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GradeDtoMapper {

    default GroupClientShortRequestDto titleToGroup(String title) {
        return new GroupClientShortRequestDto(title);
    }

    default ThemeClientShortRequestDto titleToTheme(String title) {
        return new ThemeClientShortRequestDto(title);
    }

    @Mapping(source = "groupTitle", target = "group")
    @Mapping(source = "themeTitle", target = "theme")
    GradeClientRequestDto mapRequestDtoToClientDto(GradeDtoRequest gradeDtoRequest);

    List<GradeDtoResponse> mapClientDtosToResponseDtos(List<GradeClientResponseDto> gradeClientResponseDtos);

    GradeDtoResponse mapClientDtoToResponseDto(GradeClientResponseDto gradeClientResponseDto);

}
