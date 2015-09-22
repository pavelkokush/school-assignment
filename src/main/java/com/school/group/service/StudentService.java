package com.school.group.service;

import com.school.group.model.Student;
import com.school.group.repository.StudentRepository;

import java.util.Set;

public class StudentService {
    private final StudentRepository studentRepository;

    StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Set<Student> getAll() {
        return studentRepository.getAll();
    }

}
