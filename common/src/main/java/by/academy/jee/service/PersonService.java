package by.academy.jee.service;

import by.academy.jee.dao.PersonDao;
import by.academy.jee.exception.NotFoundException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private static final String BAD_REQUEST_UPDATE_PERSON =
            "Bad request - person is null or id not equals to person.id";

    private final PasswordEncoder passwordEncoder;
    private final PersonDao personDao;

    public Person createPerson(Person person) {
        encodePasswordForPerson(person);
        return personDao.save(person);
    }

    public Person getPerson(String login) {
        return personDao.findByLogin(login).orElseThrow(NotFoundException::new);
    }

    public Person updatePerson(Person newPerson, int id) {
        if (newPerson != null && newPerson.getId() == id) {
            encodePasswordForPerson(newPerson);
            return personDao.save(newPerson);
        }
        throw new ServiceException(BAD_REQUEST_UPDATE_PERSON);
    }

    public Person removePerson(Person person) {
        personDao.delete(person);
        return person;
    }

    public List<Person> getAllPersons() {
        return personDao.findAll();
    }

    private void encodePasswordForPerson(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
    }
}