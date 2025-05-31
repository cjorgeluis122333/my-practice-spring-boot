package com.microservice.eureca.my_practice_springboot.controller.rest;

import com.microservice.eureca.my_practice_springboot.common.utili.RequestValidation;
import com.microservice.eureca.my_practice_springboot.model.entity.student.StudentEntity;
import com.microservice.eureca.my_practice_springboot.model.service.student.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RequestMapping("/api/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping({"/", "/all"})
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    ResponseEntity<?> findAllStudent() {
        return ResponseEntity.ok(studentService.findAllStudent());
    }

    @GetMapping("/")
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    ResponseEntity<?> findStudentByName(@RequestParam String name) {
        Map<String, String> error = new HashMap<>();
        try {
            var students = studentService.findStudentByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(students);
        } catch (NoSuchElementException e) {
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

        }

    }

//
//    @GetMapping("/")
//    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
//    ResponseEntity<?> findStudentByDniStudent(@RequestParam String dinStudent) {
//        Map<String, String> error = new HashMap<>();
//        try {
//            var students = studentService.findStudentByDniStudent(dinStudent);
//            return ResponseEntity.status(HttpStatus.OK).body(students);
//        } catch (NoSuchElementException e) {
//            error.put("message", e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//
//        }
//
//    }


    @PostMapping("/")
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    ResponseEntity<?> saveStudent(@RequestBody @Valid StudentEntity student, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);

        Map<String, Boolean> res = new HashMap<>();
        var wasSave = studentService.saveStudent(student);
        res.put("result", wasSave);
        return wasSave ? ResponseEntity.status(HttpStatus.OK).body(res) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);

    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    ResponseEntity<?> updateStudent(
            @RequestBody @Valid StudentEntity student,
            BindingResult bindingResult,
            @PathVariable Long id) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);

        Map<String, Boolean> res = new HashMap<>();
        var wasSave = studentService.updateStudent(student, id);
        res.put("result", wasSave);
        return wasSave ? ResponseEntity.status(HttpStatus.OK).body(res) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);

    }

    @DeleteMapping("/")

    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    ResponseEntity<?> deleteStudent(@RequestParam String dniStudent) {
        Map<String, Boolean> res = new HashMap<>();
        var wasSave = studentService.deleteStudent(dniStudent);
        res.put("result", wasSave);
        return wasSave ? ResponseEntity.status(HttpStatus.OK).body(res) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);

    }

}
