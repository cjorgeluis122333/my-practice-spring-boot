package com.microservice.eureca.my_practice_springboot.model.service.student;

import com.microservice.eureca.my_practice_springboot.model.entity.student.StudentEntity;

import java.util.List;

public interface StudentService {

    List<StudentEntity> findAllStudent();

    List<StudentEntity> findStudentByName(String firstName);

    StudentEntity findStudentByDniStudent(String dniStudent);

    Boolean saveStudent(StudentEntity student);

    Boolean updateStudent(StudentEntity student, Long dniStudent);

    Boolean deleteStudent(String dniStudent);


}
