package by.academy.jee.web.controller.rest;

import by.academy.jee.web.dto.grade.GradeDtoRequest;
import by.academy.jee.web.dto.grade.GradeDtoResponse;
import by.academy.jee.web.mapper.GradeDtoMapper;
import by.academy.jee.model.grade.Grade;
import by.academy.jee.service.facade.CollegeFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/rest/grades")
@SecurityRequirement(name = "jwtAuth")
public class GradeJsonController {

    private final CollegeFacade collegeFacade;
    private final GradeDtoMapper gradeDtoMapper;

    @GetMapping
    public List<GradeDtoResponse> getAllGrades() {
        return gradeDtoMapper.mapModelListToDtoList(collegeFacade.getAllGrades());
    }

    @PostMapping
    @Operation(summary = "Create grade")
    public ResponseEntity<GradeDtoResponse> createGrade(@Valid @RequestBody GradeDtoRequest gradeDtoRequest) {
        Grade grade = gradeDtoMapper.mapDtoToModel(gradeDtoRequest);
        return ResponseEntity.ok(gradeDtoMapper.mapModelToDto(collegeFacade.createGrade(grade)));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update grade", description = "Id in path must be equal to id in request body")
    public ResponseEntity<GradeDtoResponse> updateGrade(@Valid @RequestBody GradeDtoRequest gradeDtoRequest,
                                                        @PathVariable @Min(1) int id) {
        Grade grade = gradeDtoMapper.mapDtoToModel(gradeDtoRequest);
        return ResponseEntity.ok(gradeDtoMapper.mapModelToDto(collegeFacade.updateGrade(grade, id)));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete grade")
    public ResponseEntity<GradeDtoResponse> deleteGrade(@PathVariable @Min(1) int id) {
        return ResponseEntity.ok(gradeDtoMapper.mapModelToDto(collegeFacade.removeGrade(id)));
    }
}