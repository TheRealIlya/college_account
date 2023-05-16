package by.academy.jee.web.client;

import by.academy.jee.web.dto.client.GroupClientRequestDto;
import by.academy.jee.web.dto.client.GroupClientResponseDto;
import by.academy.jee.web.dto.client.GroupClientResponseDtoWithId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "group-service", url = "http://college-core-service:8060/groups")
public interface GroupFeignClient {

    @GetMapping
    List<GroupClientResponseDto> getAllGroups();

    @GetMapping(value = "/{title}")
    ResponseEntity<GroupClientResponseDto> getGroupByTitle(@PathVariable String title);

    @PostMapping
    ResponseEntity<GroupClientResponseDto> createOrUpdateGroup(
            @RequestBody GroupClientRequestDto groupClientRequestDto);

    @DeleteMapping(value = "/{title}")
    ResponseEntity<GroupClientResponseDto> deleteGroupByTitle(@PathVariable String title);

    @GetMapping(value = "/with_student/{id}")
    List<GroupClientResponseDtoWithId> getGroupsForStudent(@PathVariable int id);

    @GetMapping(value = "/with_teacher/{id}")
    List<GroupClientResponseDtoWithId> getGroupsForTeacher(@PathVariable int id);

    @PutMapping
    ResponseEntity<GroupClientResponseDto> patchGroup(@RequestBody GroupClientRequestDto groupClientRequestDto);

}
