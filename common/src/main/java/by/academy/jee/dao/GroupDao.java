package by.academy.jee.dao;

import by.academy.jee.model.group.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GroupDao extends MongoRepository<Group, String> {

    Optional<Group> findByTitle(String title);
}