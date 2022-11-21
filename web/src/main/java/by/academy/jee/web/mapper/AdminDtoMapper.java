package by.academy.jee.web.mapper;

import by.academy.jee.model.person.Admin;
import by.academy.jee.web.dto.person.PersonDtoRequest;
import by.academy.jee.web.dto.person.admin.AdminDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminDtoMapper extends GenericMapper<PersonDtoRequest, AdminDtoResponse, Admin> {
}
