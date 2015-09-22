package com.school.repository;

import com.school.model.Student;

import java.util.Set;

public interface StudentRepository {
    Student save(Student student);

    Set<Student> getAll();
}
