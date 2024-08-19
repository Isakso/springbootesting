package se.verran.springbootdemowithtests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;
import se.verran.springbootdemowithtests.entities.Student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SchoolServiceTest {
    private StudentService mockedStudentService;
    private SchoolService schoolService;

    @BeforeEach
    void setUp(){
        mockedStudentService = Mockito.mock(StudentService.class);
        schoolService = new SchoolService(mockedStudentService);
    }
    @Test
    void numberOfStudentsPerGroupWhenDividedIntoNumberOfGroupsWhenGroupsAreEnough() {
        when(mockedStudentService.getAllStudents())
                .thenReturn(Arrays.asList(new Student(), new Student(), new Student(), new Student()));
        String result = schoolService.numberOfStudentsPerGroupWhenDivideIntoNumberOfGroups(2);
        assertEquals("2 groups could be formed with 2 students per group", result);
    }
    @Test
    void numberOfGroupsWhenDividedIntoNumberOfGroupsWhenNotEnoughStudentsForGroups() {
           when(mockedStudentService.getAllStudents()).thenReturn(Arrays.asList(new Student()));
            String result = schoolService.numberOfStudentsPerGroupWhenDivideIntoNumberOfGroups(2);
            assertEquals("Not able to divide 1 students into 2 groups", result);
    }
    @Test
    void numberOfStudentsInTheGroupIsLessThanTwo() {
        when(mockedStudentService.getAllStudents()).thenReturn(Arrays.asList(new Student(),new Student(),new Student(),new Student()
                ,new Student(),new Student()));
        String result = schoolService.numberOfStudentsPerGroupWhenDivideIntoNumberOfGroups(1);
        assertEquals("There should be at least two groups", result);
    }
    @Test
    void numberOfStudentsPerGroupDividedIntoNumberOfGroupsWhenThereAreTooFewGroups() {
        when(mockedStudentService.getAllStudents()).thenReturn(Arrays.
                asList(new Student(), new Student(),new Student(),
                        new Student(),new Student(), new Student()));// you have two students
        String result = schoolService.numberOfStudentsPerGroupWhenDivideIntoNumberOfGroups(1);
        assertEquals("There should be at least two groups", result);
    }
    @Test
    void whenNumberOfGroupsWhenDividedIntoGroupsIsPossible() {
        when(mockedStudentService.getAllStudents()).thenReturn(Arrays.
                asList(new Student(), new Student(), new Student(), new Student(),new Student()));
        String result = schoolService.numberOfGroupsWhenDividedIntoGroupsOf(2);
        assertEquals("2 students per group is possible, there will be 2 groups, there will be 1 student hanging", result);
    }
    @Test
    void testNumberOfStudentsDividedByStudentsPerGroupWhenNumberOfStudentsIsLessThanTwo() {
        when(mockedStudentService.getAllStudents()).thenReturn(Arrays.
                asList(new Student(), new Student(), new Student(), new Student()));
        String result = schoolService.numberOfGroupsWhenDividedIntoGroupsOf(3);
        assertEquals("Not able to manage groups of 3 with only 4 students", result);
    }
    @Test
    void testNumberOfStudentsPerGroupWhenDivideIntoNumberOfGroupsWhenTheresNoRemainder() {
        when(mockedStudentService.getAllStudents()).thenReturn(Arrays.
                asList(new Student(), new Student(), new Student(), new Student(),new Student(),
                        new Student(), new Student(),new Student()));
        String result = schoolService.numberOfStudentsPerGroupWhenDivideIntoNumberOfGroups(4);
        assertEquals("4 groups could be formed with 2 students per group", result);
    }
    @Test
    void testNumberOfStudentsPerGroupWhenDivideIntoNumberOfGroupsWithOneLeftoverStudent() {
        when(mockedStudentService.getAllStudents()).thenReturn(Arrays.asList(new Student(), new Student(),new Student(), new Student(), new Student()));
        String result = schoolService.numberOfStudentsPerGroupWhenDivideIntoNumberOfGroups(2);
        assertEquals("2 groups could be formed with 2 students per group, but that would leave 1 student hanging", result);
    }
    @Test
    void calculateAverageGrade() {
        // no need to mock student class since it lacks complicated business logic
        Student student1 = new Student();
        student1.setJavaProgrammingGrade(90.0);
        Student student2 = new Student();
        student2.setJavaProgrammingGrade(80.0);
        when(mockedStudentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));
        String result = schoolService.calculateAverageGrade();
        assertEquals("Average grade is 85.0", result);
    }
    @Test
    void testCalculateAverageGradeWhenThereNoStudents() {
        when(mockedStudentService.getAllStudents()).thenReturn(Collections.emptyList());
        assertThrows(ResponseStatusException.class, () -> schoolService.calculateAverageGrade());
    }
    @Test
    void getTopScoringStudentsIsValid() {

            Student student1 = new Student();
            student1.setJavaProgrammingGrade(90.0);
            Student student2 = new Student();
            student2.setJavaProgrammingGrade(80.0);
            Student student3 = new Student();
            student3.setJavaProgrammingGrade(70.0);
            when(mockedStudentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2, student3));
            List<Student> result = schoolService.getTopScoringStudents();
            assertEquals(1, result.size());
            assertEquals(90.0, result.get(0).getJavaProgrammingGrade());
    }
}



