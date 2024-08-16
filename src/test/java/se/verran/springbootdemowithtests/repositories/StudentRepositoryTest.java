package se.verran.springbootdemowithtests.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import se.verran.springbootdemowithtests.entities.Student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;



@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void findIfStudentExistsByEmail() {
        //given
        String email = "isaac@gmail.com";
        Student student = new Student();

        student.setEmail(email);
        student.setFirstName("Isaac");
        student.setLastName("Lundberg");
        student.setBirthDate(LocalDate.of(1992,03,17));
        student.getAge();
        student.setJavaProgrammingGrade(70.0);

        studentRepository.save(student);

        //when
        boolean exists = studentRepository.existsStudentByEmail(email);

        // then
        assertThat(exists).isTrue();
    }
    @Test
    void findIfStudentDoesNotExistByEmail(){
        // given
        String email ="mat@music.com";
        //when
        boolean exists = studentRepository.existsStudentByEmail(email);
        //then
        assertThat(exists).isFalse();
   }
}