package by.academy.jee.web.facade;

import by.academy.jee.web.dto.group.GroupDtoRequest;
import by.academy.jee.web.dto.group.GroupDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupFacade {

    List<GroupDtoResponse> getAllGroups();

    GroupDtoResponse getGroup(String title);

    GroupDtoResponse createOrUpdateGroup(GroupDtoRequest groupDtoRequest);

    GroupDtoResponse deleteGroupByTitle(String title);

}
