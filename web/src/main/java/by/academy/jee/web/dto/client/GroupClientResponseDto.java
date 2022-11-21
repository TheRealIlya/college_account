package by.academy.jee.web.dto.client;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GroupClientResponseDto {

    private String title;
    private Integer teacherId;
    private List<Integer> studentIds;
    private List<String> themeTitles;
    private List<String> gradeIds;

}
