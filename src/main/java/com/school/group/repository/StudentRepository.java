package com.school.group.repository;

import com.school.group.model.Student;

import java.util.Set;

public interface StudentRepository {
    Student save(Student student);

    Set<Student> getAll();
}
