package com.microservice.eureca.my_practice_springboot.model.repository.student;

import com.microservice.eureca.my_practice_springboot.model.entity.student.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

    @Query("select s from  StudentEntity  s where s.first_name like %:name%")
    Optional<List<StudentEntity>> findStudentEntitiesByFirst_name(@Param("name") String firstName);

    @Query("select s from  StudentEntity  s where s.dni_student = :dni")
    Optional<StudentEntity> findStudentEntitiesByDni_student(@Param("dni") String dni);


}