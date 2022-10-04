package by.academy.jee.web.controller.rest;

import by.academy.jee.web.dto.grade.GradeDtoRequest;
import by.academy.jee.web.dto.grade.GradeDtoResponse;
import by.academy.jee.web.facade.GradeFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/rest/grades")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "GradeController", description = "CRUD for grades")
public class GradeJsonController {

    private final GradeFacade gradeFacade;

    @GetMapping
    public List<GradeDtoResponse> getAllGrades() {
        return gradeFacade.getAllGrades();
    }

    @PostMapping
    @Operation(summary = "Create grade")
    public ResponseEntity<GradeDtoResponse> createGrade(@Valid @RequestBody GradeDtoRequest gradeDtoRequest) {
        return ResponseEntity.ok(gradeFacade.createOrUpdateGrade(gradeDtoRequest));
    }

}
