package by.academy.jee.service;

import by.academy.jee.dao.GradeDao;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.grade.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private static final String BAD_REQUEST_UPDATE_GRADE = "Bad request - grade is null or id not equals to grade.id";

    private final GradeDao gradeDao;

    public List<Grade> getAllGrades() {
        return gradeDao.findAll();
    }

    public Grade createGrade(Grade grade) {
        return gradeDao.save(grade);
    }

    public Grade updateGrade(Grade grade, int id) {
        if (grade != null && grade.getId() == id) {
            return gradeDao.save(grade);
        }
        throw new ServiceException(BAD_REQUEST_UPDATE_GRADE);
    }

    public Grade removeGrade(int id) {
        Grade grade = gradeDao.findById(id).orElseThrow(ServiceException::new);
        gradeDao.delete(grade);
        return grade;
    }
}