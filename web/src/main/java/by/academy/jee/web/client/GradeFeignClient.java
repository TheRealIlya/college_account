package by.academy.jee.web.client;

import by.academy.jee.web.dto.client.GradeClientRequestDto;
import by.academy.jee.web.dto.client.GradeClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "grade-service", url = "http://core:8060/grades")
public interface GradeFeignClient {

    @GetMapping
    List<GradeClientResponseDto> getAllGrades();

    @PostMapping
    ResponseEntity<GradeClientResponseDto> createOrUpdateGrade(
            @RequestBody GradeClientRequestDto gradeClientRequestDto);

    @DeleteMapping(value = "/student_id/{id}")
    ResponseEntity<String> deleteGradesByStudentId(@PathVariable int id);

}
