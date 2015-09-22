package com.school.group.model;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Optional.empty;

public class Group {
    private String id;
    private String teacher;
    private Set<SchoolClass> schoolClasses = newHashSet();
    private Set<Integer> gradeLevels = newHashSet();
    private Set<Subject> subjects = newHashSet();
    private Optional<Integer> minNumOfStudents = empty();
    private Optional<Integer> maxNumOfStudents = empty();
    private Set<Student> students = newHashSet();

    public String getId() {
        return id;
    }

    public Group setId(String id) {
        this.id = id;
        return this;
    }

    public String getTeacher() {
        return teacher;
    }

    public Group setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public Set<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public Group setSchoolClasses(Set<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
        return this;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public Group setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Group setStudents(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Optional<Integer> getMinNumOfStudents() {
        return minNumOfStudents;
    }

    public Group setMinNumOfStudents(Optional<Integer> minNumOfStudents) {
        this.minNumOfStudents = minNumOfStudents;
        return this;
    }

    public Optional<Integer> getMaxNumOfStudents() {
        return maxNumOfStudents;
    }

    public Group setMaxNumOfStudents(Optional<Integer> maxNumOfStudents) {
        this.maxNumOfStudents = maxNumOfStudents;
        return this;
    }

    public Set<Integer> getGradeLevels() {
        return gradeLevels;
    }

    public Group setGradeLevels(Set<Integer> gradeLevels) {
        this.gradeLevels = gradeLevels;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
