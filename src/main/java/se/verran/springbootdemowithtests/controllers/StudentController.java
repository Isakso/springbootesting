package se.verran.springbootdemowithtests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.verran.springbootdemowithtests.entities.Student;
import se.verran.springbootdemowithtests.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/edu/api/v1")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/addstudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(studentService.addStudent(student));
    }
    @GetMapping("/getstudentbyid/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
    @GetMapping("/getallstudents")
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @PutMapping("/updatestudent")
    public ResponseEntity<Student> updateStudentById(@RequestBody Student student){
        return ResponseEntity.ok(studentService.updateStudent(student));
    }
    @PutMapping("/setgrade/{studentId}/{grade}")
    public ResponseEntity<Student> setGradeForStudentById(@PathVariable int studentId, @PathVariable String grade){
        return ResponseEntity.ok(studentService.setGradeForStudentById(studentId, grade));
    }
    @DeleteMapping("/deletestudentbyid/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable int id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student by id " + id + " deleted");
    }
}
