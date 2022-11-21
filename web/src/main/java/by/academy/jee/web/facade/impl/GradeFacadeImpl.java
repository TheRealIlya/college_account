package by.academy.jee.web.facade.impl;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Student;
import by.academy.jee.service.PersonService;
import by.academy.jee.web.client.GradeFeignClient;
import by.academy.jee.web.dto.client.GradeClientRequestDto;
import by.academy.jee.web.dto.client.GradeClientResponseDto;
import by.academy.jee.web.dto.grade.GradeDtoRequest;
import by.academy.jee.web.dto.grade.GradeDtoResponse;
import by.academy.jee.web.facade.GradeFacade;
import by.academy.jee.web.mapper.GradeDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GradeFacadeImpl implements GradeFacade {

    private static final String ERROR_GIVEN_PERSON_IS_NOT_A_STUDENT = "Error - given person is not a student";
    private final GradeFeignClient gradeFeignClient;
    private final GradeDtoMapper gradeDtoMapper;
    private final PersonService personService;

    @Override
    public List<GradeDtoResponse> getAllGrades() {
        List<GradeClientResponseDto> gradeClientDtoList = gradeFeignClient.getAllGrades();
        return gradeDtoMapper.mapClientDtosToResponseDtos(gradeClientDtoList);
    }

    @Override
    public GradeDtoResponse createOrUpdateGrade(GradeDtoRequest gradeDtoRequest) {
        Person person = personService.getPerson(gradeDtoRequest.getStudentLogin());
        if (person instanceof Student) {
            GradeClientRequestDto gradeClientRequestDto = gradeDtoMapper.mapRequestDtoToClientDto(gradeDtoRequest);
            gradeClientRequestDto.setStudentId(person.getId());
            GradeClientResponseDto clientResponseDto = gradeFeignClient.createOrUpdateGrade(gradeClientRequestDto)
                    .getBody();
            return gradeDtoMapper.mapClientDtoToResponseDto(clientResponseDto);
        }
        throw new ServiceException(ERROR_GIVEN_PERSON_IS_NOT_A_STUDENT);
    }

}
