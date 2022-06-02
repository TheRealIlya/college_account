package by.academy.jee.dao;

import by.academy.jee.model.person.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonDao extends MongoRepository<Person, String> {

    Optional<Person> findByLogin(String login);
}