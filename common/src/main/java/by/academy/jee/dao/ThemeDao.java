package by.academy.jee.dao;

import by.academy.jee.model.theme.Theme;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ThemeDao extends MongoRepository<Theme, String> {

    Optional<Theme> findByTitle(String title);
}