package by.academy.jee.service;

import by.academy.jee.dao.GroupDao;
import by.academy.jee.exception.NotFoundException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private static final String BAD_REQUEST_UPDATE_GROUP = "Bad request - group is null or id not equals to group.id";

    private final GroupDao groupDao;

    public List<Group> getAllGroups() {
        return groupDao.findAll();
    }

    public Group getGroup(String title) {
        return groupDao.findByTitle(title).orElseThrow(NotFoundException::new);
    }

    public Group createGroup(Group group) {
        return groupDao.save(group);
    }

    public Group updateGroup(Group newGroup, int id) {
        if (newGroup != null && newGroup.getId() == id) {
            return groupDao.save(newGroup);
        }
        throw new ServiceException(BAD_REQUEST_UPDATE_GROUP);
    }

    public Group removeGroup(Group group) {
        groupDao.delete(group);
        return group;
    }
}