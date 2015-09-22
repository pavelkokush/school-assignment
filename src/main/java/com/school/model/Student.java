package com.school.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Student {
    private String id;
    private SchoolClass schoolClass;
    private Integer gradeLevel;
    private Set<Subject> supposedSubjects = newHashSet();

    public String getId() {
        return id;
    }

    public Student setId(String id) {
        this.id = id;
        return this;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public Student setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
        return this;
    }

    public Set<Subject> getSupposedSubjects() {
        return supposedSubjects;
    }

    public Student setSupposedSubjects(Set<Subject> supposedSubjects) {
        this.supposedSubjects = supposedSubjects;
        return this;
    }

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public Student setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
