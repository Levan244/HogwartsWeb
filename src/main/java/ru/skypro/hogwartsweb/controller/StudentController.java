package ru.skypro.hogwartsweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы c студентами")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }


    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = service.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PostMapping("{id}")
    @Operation(summary = "Получение студента")
    public ResponseEntity<Student> getFaculty(@PathVariable Long id) {
        Student student = service.get(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping
    @Operation(summary = "Изменение студента")
    public ResponseEntity<Student> updateFaculty(@RequestBody Student student) {
        Student updateStudent = service.update(student);
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        Student student = service.remove(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping("all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("age")
    @Operation(summary = "получение студентов по возрасту")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(service.getByAge(age));
    }
}
