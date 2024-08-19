package se.verran.springbootdemowithtests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.verran.springbootdemowithtests.entities.Student;
import se.verran.springbootdemowithtests.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @Mock
    private StudentRepository mockedStudentRepository;
    @InjectMocks
    private StudentService studentService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addStudentShouldSaveStudentWhenEmailDoesNotExist() {
        //given
        Student student = new Student();
        student.setEmail("abacha@gmail.com");
        //when
        when(mockedStudentRepository.existsStudentByEmail(student.getEmail())).thenReturn(false);
        when(mockedStudentRepository.save(student)).thenReturn(student);
        //then
        Student result = studentService.addStudent(student);
        verify(mockedStudentRepository, times(1)).save(student);
        assertEquals(student, result);

    }
    @Test
    void addStudentShouldThrowConflictWhenEmailAlreadyExists() {
        // Given
        Student student = new Student();
        student.setEmail("test@example.com");
        //when
        when(mockedStudentRepository.existsStudentByEmail(student.getEmail())).thenReturn(true);
        //then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.addStudent(student));
        verify(mockedStudentRepository, never()).save(any(Student.class));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test

    void getAllStudentsShouldReturnAllStudents() {

        List<Student> students = new ArrayList<>();
        students.add(new Student()); // add a student
        when(mockedStudentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();
        verify(mockedStudentRepository, times(1)).findAll();
        assertEquals(students, result);

    }

    @Test

    void deleteStudentShouldDeleteStudentWhenIdExists() {

        int studentId = 1; // student with id 1
        when(mockedStudentRepository.existsById(studentId)).thenReturn(true);
        studentService.deleteStudent(studentId);
        verify(mockedStudentRepository, times(1)).deleteById(studentId);
    }
    @Test
    void deleteStudentShouldThrowNotFoundWhenIdDoesNotExist() {
        int studentId = 1;
        when(mockedStudentRepository.existsById(studentId)).thenReturn(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.deleteStudent(studentId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(mockedStudentRepository, never()).deleteById(anyInt());
    }
    @Test
    void updateStudentShouldUpdateStudentWhenIdExists() {

        Student student = new Student();
        student.setId(1);
        when(mockedStudentRepository.existsById(student.getId())).thenReturn(true);
        when(mockedStudentRepository.save(student)).thenReturn(student);

        Student result = studentService.updateStudent(student);

        assertEquals(student, result);
        verify(mockedStudentRepository, times(1)).save(student);
    }

    @Test
    void updateStudentShouldThrowNotFoundWhenIdDoesNotExist() {

        Student student = new Student();// given
        student.setId(1);
        when(mockedStudentRepository.existsById(student.getId())).thenReturn(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.updateStudent(student));
        verify(mockedStudentRepository, never()).save(any(Student.class));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }
    @Test
    void getStudentByIdShouldReturnStudentWhenIdExists() {
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        when(mockedStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Student result = studentService.getStudentById(studentId);
        assertEquals(student, result);
        verify(mockedStudentRepository, times(1)).findById(studentId);
    }
    @Test
    void getStudentByIdShouldThrowNotFoundWhenIdDoesNotExist() {

        int studentId = 1;
        when(mockedStudentRepository.findById(studentId)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.getStudentById(studentId));
        verify(mockedStudentRepository, times(1)).findById(studentId);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }

    @Test
    void setGradeForStudentByIdShouldUpdateGradeWhenValidGradeAndIdExists() {

        int studentId = 1;
        String gradeAsString = "4.0";
        Student student = new Student();
        student.setId(studentId);
        when(mockedStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(mockedStudentRepository.save(student)).thenReturn(student);
        Student result = studentService.setGradeForStudentById(studentId, gradeAsString);
        verify(mockedStudentRepository, times(1)).save(student);
        assertEquals(4.0, result.getJavaProgrammingGrade());
    }
    @Test
    void setGradeForStudentByIdShouldThrowNotAcceptableWhenGradeIsInvalid() {

        int studentId = 1;
        String gradeAsString = "invalidGrade";
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.setGradeForStudentById(studentId, gradeAsString));
        verify(mockedStudentRepository, never()).save(any(Student.class));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());
    }
    @Test
    void setGradeForStudentByIdShouldThrowNotAcceptableWhenGradeIsOutOfRange() {
        int studentId = 1;
        String gradeAsString = "A";
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> studentService.setGradeForStudentById(studentId, gradeAsString));
        verify(mockedStudentRepository, never()).save(any(Student.class));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatusCode());

    }

}