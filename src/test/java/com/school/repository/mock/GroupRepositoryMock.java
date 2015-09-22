package com.school.repository.mock;

import com.school.model.Group;
import com.school.repository.GroupRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.google.common.collect.Maps.newHashMap;
import static org.assertj.core.util.Sets.newHashSet;

public class GroupRepositoryMock implements GroupRepository {
    private final Map<String, Group> groups = newHashMap();

    @Override
    public Set<Group> getAll() {
        return newHashSet(groups.values());
    }

    @Override
    public Group save(Group group) {
        if (group.getId() == null) {
            group.setId(UUID.randomUUID().toString());
        }

        groups.put(group.getId(), group);

        return group;
    }

    @Override
    public Group get(String id) {
        return groups.get(id);
    }

    public void clear() {
        groups.clear();
    }
}
