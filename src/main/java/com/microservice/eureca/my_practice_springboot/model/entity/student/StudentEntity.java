package com.microservice.eureca.my_practice_springboot.model.entity.student;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String first_name;

    @Column(length = 30, nullable = false)
    private String last_name;

    @Column(length = 11, nullable = false, unique = true)
    private String dni_student;

    @Column(name = "carrera", length = 20, nullable = false)
    private String career;

    private int sch_year;

    private Boolean scholarship;

    @Column(name = "adress")
    private String address;

    public StudentEntity() {

    }

    @PrePersist
    public void prePersist() {
        sch_year = 1;
        scholarship = false;
    }


    public StudentEntity(String first_name, String last_name, String dni_student, String career, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dni_student = dni_student;
        this.career = career;
        this.address = address;
        sch_year = 1;
        scholarship = false;
    }

    public StudentEntity(String first_name, String last_name, String dni_student, String career) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dni_student = dni_student;
        this.career = career;
        sch_year = 1;
        scholarship = false;
    }

    public StudentEntity(String first_name, String last_name, String dni_student, String career, int sch_year, Boolean scholarship, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dni_student = dni_student;
        this.career = career;
        this.sch_year = sch_year;
        this.scholarship = scholarship;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDni_student() {
        return dni_student;
    }

    public void setDni_student(String dni_student) {
        this.dni_student = dni_student;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getSch_year() {
        return sch_year;
    }

    public void setSch_year(int sch_year) {
        this.sch_year = sch_year;
    }

    public Boolean getScholarship() {
        return scholarship;
    }

    public void setScholarship(Boolean scholarship) {
        this.scholarship = scholarship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
