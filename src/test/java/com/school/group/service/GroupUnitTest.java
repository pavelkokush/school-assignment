package com.school.group.service;

import com.school.group.model.Group;
import com.school.group.model.SchoolClass;
import com.school.group.model.Student;
import com.school.group.model.Subject;
import com.school.group.repository.mock.GroupRepositoryMock;
import com.school.group.repository.mock.StudentRepositoryMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;


public class GroupUnitTest {
    private static GroupService groupService;

    private static GroupRepositoryMock groupRepository;
    private static StudentRepositoryMock studentRepository;

    @BeforeClass
    public static void setUp() throws Exception {
        groupRepository = new GroupRepositoryMock();
        studentRepository = new StudentRepositoryMock();

        groupService = new GroupService(new StudentService(studentRepository), groupRepository);
    }

    @Before
    public void clean() throws Exception {
        groupRepository.clear();
        studentRepository.clear();
    }

    @Test
    public void expectGroupPopulatedSuccessfully() {
        //given
        SchoolClass class1A = getSchoolClass("1a");
        Group group = groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.MATH)).setGradeLevels(newHashSet(1))
                        .setSchoolClasses(newHashSet(class1A)));
        Student jon = studentRepository.save(
                new Student().setSchoolClass(class1A).setGradeLevel(1).setSupposedSubjects(newHashSet(Subject.MATH)));

        //when
        groupService.populate();

        //then
        group = groupRepository.get(group.getId());
        assertThat(group.getStudents()).containsExactly(jon);
    }

    @Test
    public void expectGroupNotPopulatedWhenSubjectNotMatch() {
        //given
        SchoolClass class1A = getSchoolClass("1a");
        Group group = groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.MATH)).setGradeLevels(newHashSet(1))
                        .setSchoolClasses(newHashSet(class1A)));
        studentRepository.save(
                new Student().setSchoolClass(class1A).setGradeLevel(1).setSupposedSubjects(newHashSet(Subject.ENGLISH)));

        //when
        groupService.populate();

        //then
        group = groupRepository.get(group.getId());
        assertThat(group.getStudents()).isEmpty();
    }

    @Test
    public void expectGroupNotPopulatedWhenSchoolClassNotMatch() {
        //given
        SchoolClass class1A = getSchoolClass("1a");
        SchoolClass class1B = getSchoolClass("1b");
        Group group = groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.MATH)).setGradeLevels(newHashSet(1))
                        .setSchoolClasses(newHashSet(class1A)));
        studentRepository.save(
                new Student().setSchoolClass(class1B).setGradeLevel(1).setSupposedSubjects(newHashSet(Subject.MATH)));

        //when
        groupService.populate();

        //then
        group = groupRepository.get(group.getId());
        assertThat(group.getStudents()).isEmpty();
    }

    @Test
    public void expectGroupNotPopulatedWhenGradeLevelNotMatch() {
        //given
        SchoolClass class1A = getSchoolClass("1a").setGradeLevels(newHashSet(1, 2, 3));
        Group group = groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.MATH)).setSchoolClasses(newHashSet(class1A))
                        .setGradeLevels(newHashSet(1, 2)));

        studentRepository.save(
                new Student().setSchoolClass(class1A).setGradeLevel(3).setSupposedSubjects(newHashSet(Subject.MATH)));

        //when
        groupService.populate();

        //then
        group = groupRepository.get(group.getId());
        assertThat(group.getStudents()).isEmpty();
    }

    @Test
    public void expectGroupNotPopulatedWhenMoreThenOneGroupWithSameSubjectAreMatch() {
        //given
        SchoolClass class1A = getSchoolClass("1a");
        Group group1 = groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.MATH)).setGradeLevels(newHashSet(1))
                        .setSchoolClasses(newHashSet(class1A)));
        Group group2 = groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.MATH)).setGradeLevels(newHashSet(1))
                        .setSchoolClasses(newHashSet(class1A)));

        studentRepository.save(
                new Student().setSchoolClass(class1A).setGradeLevel(1).setSupposedSubjects(newHashSet(Subject.MATH)));

        //when
        groupService.populate();

        //then
        group1 = groupRepository.get(group1.getId());
        assertThat(group1.getStudents()).isEmpty();

        group2 = groupRepository.get(group2.getId());
        assertThat(group2.getStudents()).isEmpty();
    }

    @Test
    public void expectGroupNotPopulatedWhenNotEnoughStudent() {
        //given
        SchoolClass class1A = getSchoolClass("1a");
        Group group = groupRepository.save(
                new Group().setMinNumOfStudents(Optional.of(2)).setSubjects(newHashSet(Subject.MATH))
                        .setGradeLevels(newHashSet(1)).setSchoolClasses(newHashSet(class1A)));

        studentRepository.save(new Student().setSchoolClass(class1A).setGradeLevel(1)
                .setSupposedSubjects(newHashSet(Subject.MATH)));

        //when
        groupService.populate();

        //then
        group = groupRepository.get(group.getId());
        assertThat(group.getStudents()).isEmpty();
    }

    @Test
    public void expectGroupNotPopulatedWhenTooManyStudentsInGroup() {
        //given
        SchoolClass class1A = getSchoolClass("1a");
        Group group = groupRepository.save(
                new Group().setMaxNumOfStudents(Optional.of(0)).setSubjects(newHashSet(Subject.MATH))
                        .setGradeLevels(newHashSet(1)).setSchoolClasses(newHashSet(class1A)));
        studentRepository.save(
                new Student().setSchoolClass(class1A).setGradeLevel(1).setSupposedSubjects(newHashSet(Subject.MATH)));

        //when
        groupService.populate();

        //then
        group = groupRepository.get(group.getId());
        assertThat(group.getStudents()).isEmpty();
    }

    @Test
    public void expectGetOneUnplacedStudentThatDoNotMatchAnyGroups() {
        //given
        SchoolClass class1A = getSchoolClass("1a");
        groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.MATH)).setGradeLevels(newHashSet(1))
                        .setSchoolClasses(newHashSet(class1A)));

        SchoolClass class3a = getSchoolClass("3a");
        groupRepository.save(
                new Group().setSubjects(newHashSet(Subject.ENGLISH)).setGradeLevels(newHashSet(1))
                        .setSchoolClasses(newHashSet(class3a)));

        Student jon = studentRepository.save(
                new Student().setSchoolClass(class1A).setGradeLevel(1).setSupposedSubjects(newHashSet(Subject.MATH)));

        Student tom = studentRepository.save(
                new Student().setSchoolClass(class3a).setGradeLevel(1).setSupposedSubjects(newHashSet(Subject.MATH)));

        groupService.populate();

        //when
        Set<Student> unplacedStudents = groupService.findUnplacedStudents();

        //then
        assertThat(unplacedStudents).containsExactly(tom);
    }

    private SchoolClass getSchoolClass(String name) {
        int gradeLevel = Integer.parseInt(name.substring(0, 1));
        return new SchoolClass().setId("id_" + name).setGradeLevels(newHashSet(gradeLevel)).setName(name);
    }
}
