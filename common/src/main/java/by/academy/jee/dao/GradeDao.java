package by.academy.jee.dao;

import by.academy.jee.model.grade.Grade;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GradeDao extends MongoRepository<Grade, String> {
}