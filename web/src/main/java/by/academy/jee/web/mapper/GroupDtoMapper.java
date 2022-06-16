package by.academy.jee.web.mapper;

import by.academy.jee.web.dto.group.GroupDtoRequest;
import by.academy.jee.web.dto.group.GroupDtoResponse;
import by.academy.jee.model.group.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupDtoMapper extends GenericMapper<GroupDtoRequest, GroupDtoResponse, Group> {
}