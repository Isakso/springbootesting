package se.verran.springbootdemowithtests.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.verran.springbootdemowithtests.entities.Student;

import java.util.List;

@Service
public class SchoolService {


    private final StudentService studentService;
    @Autowired
    public SchoolService(StudentService studentService) {
        this.studentService = studentService;
    }

    // How many groups I would like, don't know how many students
    public String numberOfStudentsPerGroupWhenDivideIntoNumberOfGroups(int numberOfGroups) {
        int numberOfStudents = studentService.getAllStudents().size();
        if(numberOfGroups < 2)
            return "There should be at least two groups";
        if(numberOfGroups > numberOfStudents)
            return String.format("Not able to divide %s students into %s groups", numberOfStudents, numberOfGroups);
        int studentsPerGroup = numberOfStudents / numberOfGroups;
        if(studentsPerGroup < 2)
            return String.format("Not able to manage %s groups with %s students", numberOfGroups, numberOfStudents);
        int remainder = numberOfStudents % numberOfGroups;
        return String.format("%s groups could be formed with %s students per group%s",
                numberOfGroups,
                studentsPerGroup,
        (remainder == 0 ? "" : String.format(", but that would leave %s student" + (remainder == 1 ? "" : "s") + " hanging", remainder)));
    }
    // How many students per group I would like, don't know how many students and how many groups I end up with
    public String numberOfGroupsWhenDividedIntoGroupsOf(int studentsPerGroup){
        int numberOfStudents = studentService.getAllStudents().size();
        if(studentsPerGroup < 2)
            return "Size of group should be at least 2";
        if(numberOfStudents < studentsPerGroup || numberOfStudents / studentsPerGroup < 2)
            return String.format("Not able to manage groups of %s with only %s students", studentsPerGroup, numberOfStudents);
        int numberOfGroups = numberOfStudents / studentsPerGroup;
        int remainder = numberOfStudents % studentsPerGroup;
        return String.format("%s students per group is possible, there will be %s groups" +
                (remainder == 0 ? "":", there will be " + remainder + " student" + (remainder == 1 ? "" : "s") + " hanging"),
                studentsPerGroup, numberOfGroups,
                (remainder == 0 ? "":String.format(", there will be %s student" + (remainder == 1 ? "" : "s") + " hanging", remainder)));
    }

    public String calculateAverageGrade() {
        List<Student> studentList = studentService.getAllStudents();
        if(studentList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found");
        double totalSum = 0.0;
        for (Student student: studentList)
            totalSum += student.getJavaProgrammingGrade();
        double average = totalSum / studentList.size();
        return String.format("Average grade is %.1f", average);
    }

    public List<Student> getTopScoringStudents() {
        List<Student> studentList = studentService.getAllStudents();
        if(studentList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found");
        List<Student> sortedStudentList = studentList
                .stream()
                .sorted((student1, student2)->Double.compare(student2.getJavaProgrammingGrade(), student1.getJavaProgrammingGrade()))
                .toList();
        int numberOfTopStudents = (int)Math.ceil(sortedStudentList.size()*0.2);
        return sortedStudentList.subList(0, numberOfTopStudents);
    }
}
