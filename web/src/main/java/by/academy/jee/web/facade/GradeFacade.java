package by.academy.jee.web.facade;

import by.academy.jee.web.dto.grade.GradeDtoRequest;
import by.academy.jee.web.dto.grade.GradeDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeFacade {

    List<GradeDtoResponse> getAllGrades();

    GradeDtoResponse createOrUpdateGrade(GradeDtoRequest gradeDtoRequest);

}
