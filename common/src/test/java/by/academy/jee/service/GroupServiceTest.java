package by.academy.jee.service;

import by.academy.jee.dao.GroupDao;
import by.academy.jee.exception.NotFoundException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupServiceTest {

    private final GroupDao groupDao = mock(GroupDao.class);
    private final GroupService groupService = new GroupService(groupDao);

    @AfterEach
    void clearMocks() {
        clearInvocations();
    }

    @Test
    void getGroupWhenInputTitleIsValid() {
        Group expected = new Group();
        expected.setTitle("Valid group title");
        Group actual = getGroup(expected.getTitle());
        assertEquals(expected, actual);
    }

    @Test
    void getGroupWhenInputTitleIsNotValid() {
        assertThrows(NotFoundException.class, () -> getGroup("Wrong group title"));
    }

    @Test
    void updateGroupWhenAllConditionsValid() {
        Group expected = new Group();
        expected.setId(2);
        Group actual = updateGroup(expected, 2);
        assertEquals(expected, actual);
    }

    @Test
    void updateGroupWhenIdIsNotEqualToGroupId() {
        Group group = new Group();
        group.setId(2);
        assertThrows(ServiceException.class, () -> updateGroup(group, 3));
    }

    @Test
    void updateGroupWhenGroupIsNull() {
        assertThrows(ServiceException.class, () -> groupService.updateGroup(null, 3));
    }

    private Group getGroup(String title) {
        String validTitle = "Valid group title";
        when(groupDao.findByTitle(title)).then(invocation -> {
            if (validTitle.equals(title)) {
                Group group = new Group();
                group.setTitle(title);
                return Optional.of(group);
            }
            return Optional.empty();
        });
        return groupService.getGroup(title);
    }

    private Group updateGroup(Group group, int id) {
        when(groupDao.save(group)).thenReturn(group);
        return groupService.updateGroup(group, id);
    }
}