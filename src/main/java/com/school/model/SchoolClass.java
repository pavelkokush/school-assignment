package com.school.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class SchoolClass {
    private String id;
    private String name;
    private Set<Integer> gradeLevels = newHashSet();
    private Set<Student> students = newHashSet();

    public String getId() {
        return id;
    }

    public SchoolClass setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SchoolClass setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Integer> getGradeLevels() {
        return gradeLevels;
    }

    public SchoolClass setGradeLevels(Set<Integer> gradeLevels) {
        this.gradeLevels = gradeLevels;
        return this;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public SchoolClass setStudents(Set<Student> students) {
        this.students = students;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolClass that = (SchoolClass) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
