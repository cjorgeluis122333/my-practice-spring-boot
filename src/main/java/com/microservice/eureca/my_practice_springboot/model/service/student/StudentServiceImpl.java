package com.microservice.eureca.my_practice_springboot.model.service.student;

import com.microservice.eureca.my_practice_springboot.model.entity.student.StudentEntity;
import com.microservice.eureca.my_practice_springboot.model.repository.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentEntity> findAllStudent() {
        return (List<StudentEntity>) studentRepository.findAll();

    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentEntity> findStudentByName(String firstName) {
        return studentRepository.findStudentEntitiesByFirst_name(firstName).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public StudentEntity findStudentByDniStudent(String dniStudent) {
        return studentRepository.findStudentEntitiesByDni_student(dniStudent).orElseThrow();
    }

    @Transactional
    @Override
    public Boolean saveStudent(StudentEntity student) {
        var tempStudent = studentRepository.findStudentEntitiesByDni_student(student.getDni_student());
        if (tempStudent.isPresent()) {
            return false;
        } else {
            studentRepository.save(student);
            return true;
        }
    }


    @Transactional
    @Override
    public Boolean updateStudent(StudentEntity student, Long dniStudent) {
        var tempStudent = studentRepository.findStudentEntitiesByDni_student(student.getDni_student());
        if (tempStudent.isPresent()) {
            studentRepository.save(student);
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    @Override
    public Boolean deleteStudent(String dniStudent) {
        var tempStudent = studentRepository.findStudentEntitiesByDni_student(dniStudent);

        if (tempStudent.isPresent()) {
            studentRepository.delete(tempStudent.orElseThrow());
            return true;
        } else return false;


    }
}
