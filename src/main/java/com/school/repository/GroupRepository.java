package com.school.repository;

import com.school.model.Group;

import java.util.Set;

public interface GroupRepository {
    Set<Group> getAll();

    Group save(Group group);

    Group get(String id);
}
