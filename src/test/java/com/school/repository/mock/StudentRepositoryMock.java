package com.school.repository.mock;

import com.school.model.Student;
import com.school.repository.StudentRepository;

import java.util.Set;
import java.util.UUID;

import static com.google.common.collect.Sets.newHashSet;

public class StudentRepositoryMock implements StudentRepository {
    private final Set<Student> students = newHashSet();

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(UUID.randomUUID().toString());
        }

        students.add(student);

        return student;
    }

    public Set<Student> getAll() {
        return students;
    }

    public void clear() {
        students.clear();
    }

}
