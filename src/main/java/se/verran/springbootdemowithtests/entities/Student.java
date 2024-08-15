package se.verran.springbootdemowithtests.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 1)
    private Double javaProgrammingGrade;

    public Student() {
    }

    public Student(String firstName, String lastName, LocalDate birthDate, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge(){
        LocalDate today = LocalDate.now();
        Period agePeriod = Period.between(birthDate, today);
        return agePeriod.getYears();
    }

    public Double getJavaProgrammingGrade() {
        return javaProgrammingGrade;
    }

    public void setJavaProgrammingGrade(Double javaProgrammingGrade) {
        this.javaProgrammingGrade = javaProgrammingGrade;
    }
}
