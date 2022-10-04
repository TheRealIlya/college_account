package by.academy.jee.web.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GroupClientResponseDtoWithId {

    private String id;
    private String title;

    @Setter
    private Integer teacherId;
    private List<Integer> studentIds;
    private List<String> themeTitles;
    private List<String> gradeIds;

}
