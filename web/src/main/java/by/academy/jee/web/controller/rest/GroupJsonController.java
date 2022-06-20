package by.academy.jee.web.controller.rest;

import by.academy.jee.web.dto.group.GroupDtoRequest;
import by.academy.jee.web.dto.group.GroupDtoResponse;
import by.academy.jee.web.mapper.GroupDtoMapper;
import by.academy.jee.model.group.Group;
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
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/rest/groups")
@SecurityRequirement(name = "jwtAuth")
public class GroupJsonController {

    private final CollegeFacade collegeFacade;
    private final GroupDtoMapper groupDtoMapper;

    @GetMapping
    public List<GroupDtoResponse> getAllGroups() {
        return groupDtoMapper.mapModelListToDtoList(collegeFacade.getAllGroups());
    }

    @GetMapping(value = "/{title}")
    @Operation(summary = "Get group by title")
    public ResponseEntity<GroupDtoResponse> getGroup(@PathVariable @NotNull String title) {
        return ResponseEntity.ok(groupDtoMapper.mapModelToDto(collegeFacade.getGroup(title)));
    }

    @PostMapping
    @Operation(summary = "Create group",
            description = "Accept group in request body, cause 400 if input is invalid or title is already exist")
    public ResponseEntity<GroupDtoResponse> createGroup(@Valid @RequestBody GroupDtoRequest groupDtoRequest) {
        Group group = groupDtoMapper.mapDtoToModel(groupDtoRequest);
        return ResponseEntity.ok(groupDtoMapper.mapModelToDto(collegeFacade.createGroup(group)));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update group", description = "Id in path must be equal to id in request body")
    public ResponseEntity<GroupDtoResponse> updateGroup(@Valid @RequestBody GroupDtoRequest groupDtoRequest,
                                                        @PathVariable @Min(1) int id) {
        Group group = groupDtoMapper.mapDtoToModel(groupDtoRequest);
        return ResponseEntity.ok(groupDtoMapper.mapModelToDto(collegeFacade.updateGroup(group, id)));
    }

    @DeleteMapping(value = "/{title}")
    @Operation(summary = "Delete group", description = "Includes deletion of group's grades")
    public ResponseEntity<GroupDtoResponse> deleteGroup(@PathVariable @NotNull String title) {
        Group group = collegeFacade.removeGroupByTitle(title);
        return ResponseEntity.ok(groupDtoMapper.mapModelToDto(group));
    }
}