package by.academy.jee.web.controller.rest;

import by.academy.jee.model.person.Person;
import by.academy.jee.service.facade.CollegeFacade;
import by.academy.jee.web.dto.person.PersonDtoRequest;
import by.academy.jee.web.dto.person.PersonDtoResponse;
import by.academy.jee.web.mapper.PersonDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/rest/persons")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "PersonController", description = "CRUD for persons")
public class PersonJsonController {

    private final CollegeFacade collegeFacade;
    private final PersonDtoMapper personDtoMapper;

    @GetMapping
    public List<PersonDtoResponse> getAllPersons() {
        return personDtoMapper.mapModelListToDtoList(collegeFacade.getAllPersons());
    }

    @GetMapping(value = "/{login}")
    @Operation(summary = "Get person by login")
    public ResponseEntity<PersonDtoResponse> getPerson(@PathVariable @NotNull String login) {
        Person person = collegeFacade.getPerson(login);
        return ResponseEntity.ok(personDtoMapper.mapModelToDto(person));
    }

    @PostMapping
    @Operation(summary = "Create person",
            description = "Accept person in request body, cause 400 if input is invalid or login is already exist")
    public ResponseEntity<PersonDtoResponse> createPerson(@Valid @RequestBody PersonDtoRequest personDtoRequest) {
        Person person = personDtoMapper.mapDtoToModel(personDtoRequest);
        return ResponseEntity.ok(personDtoMapper.mapModelToDto(collegeFacade.createPerson(person)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update person", description = "Id in path must be equal to id in request body")
    public ResponseEntity<PersonDtoResponse> updatePerson(@Valid @RequestBody PersonDtoRequest personDtoRequest,
                                                          @PathVariable @Min(1) int id) {
        Person person = personDtoMapper.mapDtoToModel(personDtoRequest);
        return ResponseEntity.ok(personDtoMapper.mapModelToDto(collegeFacade.updatePerson(person, id)));
    }

    @DeleteMapping(value = "/{login}")
    @Operation(summary = "Delete person")
    public ResponseEntity<PersonDtoResponse> deletePerson(@PathVariable @NotNull String login) {
        Person person = collegeFacade.getPerson(login);
        return ResponseEntity.ok(personDtoMapper.mapModelToDto(collegeFacade.removePerson(person)));
    }
}
