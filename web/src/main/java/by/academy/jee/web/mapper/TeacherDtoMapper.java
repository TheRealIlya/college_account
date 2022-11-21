package by.academy.jee.web.mapper;

import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.dto.person.PersonDtoRequest;
import by.academy.jee.web.dto.person.teacher.TeacherDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherDtoMapper extends GenericMapper<PersonDtoRequest, TeacherDtoResponse, Teacher> {
}
