package com.school.service;

import com.school.model.Student;
import com.school.repository.StudentRepository;

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
