package com.school.group.repository;

import com.school.group.model.Group;

import java.util.Set;

public interface GroupRepository {
    Set<Group> getAll();

    Group save(Group group);

    Group get(String id);
}
