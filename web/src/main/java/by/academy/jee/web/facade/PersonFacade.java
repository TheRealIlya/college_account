package by.academy.jee.web.facade;

import by.academy.jee.web.dto.person.PersonDtoRequest;
import by.academy.jee.web.dto.person.PersonDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonFacade {

    List<PersonDtoResponse> getAllPersons();

    PersonDtoResponse getPerson(String login);

    PersonDtoResponse createPerson(PersonDtoRequest personDtoRequest);

    PersonDtoResponse updatePerson(PersonDtoRequest personDtoRequest, int id);

    PersonDtoResponse deletePerson(String login);

}
