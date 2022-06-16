package by.academy.jee.service;

import by.academy.jee.dao.PersonDao;
import by.academy.jee.exception.NotFoundException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonServiceTest {

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final PersonDao personDao = mock(PersonDao.class);
    private final PersonService personService = new PersonService(passwordEncoder, personDao);

    @AfterEach
    void clearMocks() {
        clearInvocations();
    }

    @Test
    void createPersonWhenLoginIsNew() {
        Person expected = new Admin();
        expected.setLogin("New Login");
        expected.setPassword("Some password");
        when(passwordEncoder.encode(expected.getPassword())).thenReturn(expected.getPassword());
        when(personDao.save(expected)).thenReturn(expected);
        Person actual = personService.createPerson(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getPersonWhenLoginIsValid() {
        Student expected = new Student();
        Person actual = getPerson("Valid login");
        assertEquals(expected, actual);
    }

    @Test
    void getPersonWhenLoginIsNotValid() {
        assertThrows(NotFoundException.class, () -> getPerson("Wrong login"));
    }

    @Test
    void updatePersonWhenAllConditionsValid() {
        Person expected = new Admin();
        expected.setId(4);
        expected.setPassword("Some password");
        Person actual = updatePerson(expected, 4);
        assertEquals(expected, actual);
    }

    @Test
    void updatePersonWhenIdIsNotEqualToPersonId() {
        Person person = new Admin();
        person.setId(4);
        person.setPassword("Some password");
        assertThrows(ServiceException.class, () -> updatePerson(person, 5));
    }

    @Test
    void updatePersonWhenPersonIsNull() {
        assertThrows(ServiceException.class, () -> personService.updatePerson(null, 4));
    }

    private Person getPerson(String login) {
        when(personDao.findByLogin(login)).then(invocation -> {
            if ("Valid login".equals(login)) {
                return Optional.of(new Student());
            }
            throw new NotFoundException();
        });
        return personService.getPerson(login);
    }

    private Person updatePerson(Person person, int id) {
        when(passwordEncoder.encode(person.getPassword())).thenReturn(person.getPassword());
        when(personDao.save(person)).thenReturn(person);
        return personService.updatePerson(person, id);
    }
}