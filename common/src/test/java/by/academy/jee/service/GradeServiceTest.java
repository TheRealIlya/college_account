package by.academy.jee.service;

import by.academy.jee.dao.GradeDao;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.grade.Grade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GradeServiceTest {

    private final GradeDao gradeDao = mock(GradeDao.class);
    private final GradeService gradeService = new GradeService(gradeDao);

    @AfterEach
    void clearMocks() {
        clearInvocations();
    }

    @Test
    void updateGradeWhenAllConditionsValid() {
        Grade expected = new Grade();
        expected.setId(2);
        Grade actual = updateGrade(expected, 2);
        assertEquals(expected, actual);
    }

    @Test
    void updateGradeWhenIdIsNotEqualToGradeId() {
        Grade grade = new Grade();
        grade.setId(3);
        assertThrows(ServiceException.class, () -> updateGrade(grade, 4));
    }

    @Test
    void updateGradeWhenGradeIsNull() {
        assertThrows(ServiceException.class, () -> gradeService.updateGrade(null, 2));
    }

    @Test
    void removeGradeWhenInputIdIsValid() {
        removeGrade(1);
        assertTrue(true);
    }

    @Test
    void removeGradeWhenInputIsNotValid() {
        assertThrows(ServiceException.class, () -> removeGrade(2));
    }

    private Grade updateGrade(Grade grade, int id) {
        when(gradeDao.save(grade)).thenReturn(grade);
        return gradeService.updateGrade(grade, id);
    }

    private void removeGrade(int id) {
        int validId = 1;
        when(gradeDao.findById(id)).then(invocation -> {
            if (validId == id) {
                return Optional.of(new Grade());
            }
            return Optional.empty();
        });
        doNothing().when(gradeDao).delete(any(Grade.class));
        gradeService.removeGrade(id);
    }
}