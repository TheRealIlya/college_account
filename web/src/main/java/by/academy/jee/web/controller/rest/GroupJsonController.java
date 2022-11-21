package by.academy.jee.web.controller.rest;

import by.academy.jee.web.dto.group.GroupDtoRequest;
import by.academy.jee.web.dto.group.GroupDtoResponse;
import by.academy.jee.web.facade.GroupFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/rest/groups")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "GroupController", description = "CRUD for groups")
public class GroupJsonController {

    private final GroupFacade groupFacade;

    @GetMapping
    public List<GroupDtoResponse> getAllGroups() {
        return groupFacade.getAllGroups();
    }

    @GetMapping(value = "/{title}")
    @Operation(summary = "Get group by title")
    public ResponseEntity<GroupDtoResponse> getGroup(@PathVariable @NotNull String title) {
        return ResponseEntity.ok(groupFacade.getGroup(title));
    }

    @PostMapping
    @Operation(summary = "Create or update group",
            description = "Accept group in request body")
    public ResponseEntity<GroupDtoResponse> createOrUpdateGroup(@Valid @RequestBody GroupDtoRequest groupDtoRequest) {
        return ResponseEntity.ok(groupFacade.createOrUpdateGroup(groupDtoRequest));
    }

    @DeleteMapping(value = "/{title}")
    @Operation(summary = "Delete group", description = "Includes deletion of group's grades")
    public ResponseEntity<GroupDtoResponse> deleteGroup(@PathVariable @NotNull String title) {
        return ResponseEntity.ok(groupFacade.deleteGroupByTitle(title));
    }

}
