package com.school.group.service;

import com.school.group.model.Group;
import com.school.group.model.Student;
import com.school.group.repository.GroupRepository;

import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

public class GroupService {
    private final StudentService studentService;
    private final GroupRepository groupRepository;

    GroupService(StudentService studentService, GroupRepository groupRepository) {
        this.studentService = studentService;
        this.groupRepository = groupRepository;
    }

    public void populate() {
        Set<Student> allStudents = studentService.getAll();

        for (Student student : allStudents) {
            Set<Group> groups = groupRepository.getAll().stream()
                    .filter(bySchoolClassOf(student))
                    .filter(bySubjectOf(student))
                    .filter(byGradeLevelOf(student))
                    .collect(toSet());

            if (groups.size() == 1) {
                Group group = groups.stream().findFirst().get();
                group.getStudents().add(student);
                groupRepository.save(group);
            }
        }

        groupRepository.getAll().stream()
                .filter(groupThatHasNotEnoughStudents().or(groupsThatHasTooManyStudents()))
                .forEach(group -> {
                    group.getStudents().clear();
                    groupRepository.save(group);
                });
    }

    public Set<Student> findUnplacedStudents() {
        Set<Student> studentsInGroups = groupRepository.getAll().stream()
                .flatMap(group -> group.getStudents().stream())
                .collect(toSet());

        return studentService.getAll().stream()
                .filter(student -> !studentsInGroups.contains(student))
                .collect(toSet());
    }

    private Predicate<Group> groupsThatHasTooManyStudents() {
        return group -> group.getMinNumOfStudents().isPresent()
                && group.getMinNumOfStudents().get() > group.getStudents().size();
    }

    private Predicate<Group> groupThatHasNotEnoughStudents() {
        return group -> group.getMaxNumOfStudents().isPresent()
                && group.getMaxNumOfStudents().get() < group.getStudents().size();
    }

    private Predicate<Group> bySubjectOf(Student student) {
        return group -> student.getSupposedSubjects().containsAll(group.getSubjects());
    }

    private Predicate<Group> bySchoolClassOf(Student student) {
        return group -> group.getSchoolClasses().contains(student.getSchoolClass());
    }

    private Predicate<Group> byGradeLevelOf(Student student) {
        return group -> group.getGradeLevels().contains(student.getGradeLevel());
    }
}
