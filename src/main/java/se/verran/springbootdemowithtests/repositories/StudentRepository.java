package se.verran.springbootdemowithtests.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.verran.springbootdemowithtests.entities.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // A "self-made" method, either query method or a native query, has to be tested
    boolean existsStudentByEmail(String email);
}
