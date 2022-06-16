package by.academy.jee.web.mapper;

import by.academy.jee.web.dto.person.PersonDtoRequest;
import by.academy.jee.web.dto.person.student.StudentDtoResponse;
import by.academy.jee.model.person.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentDtoMapper extends GenericMapper<PersonDtoRequest, StudentDtoResponse, Student> {
}