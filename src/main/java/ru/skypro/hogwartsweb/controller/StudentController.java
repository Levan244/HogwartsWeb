package ru.skypro.hogwartsweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.hogwartsweb.model.Faculty;
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
    @GetMapping("{id}")
    @Operation(summary = "Получение студента")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student foundStudent = service.get(id);
        return ResponseEntity.ok(foundStudent);
    }


    @PostMapping("all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("age")
    @Operation(summary = "получение студентов по возрасту")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer startAge,
                                                                @RequestParam Integer engAge) {
        return ResponseEntity.ok(service.getByAge(startAge, engAge));
    }


    @GetMapping("faculty/{studentId}")
    public ResponseEntity<Faculty> getStudentFuculty(@PathVariable Long studentId) {
        Faculty faculty = service.get(studentId).getFaculty();
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("could")
    @Operation(summary = "Получение количество студентов")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(service.getCount());
    }
    @GetMapping("age/average")
    @Operation(summary = "Получение среднего возраста студентов")
    public ResponseEntity<Float> getAverageAge() {
        return ResponseEntity.ok(service.getAverageAge());
    }
    @GetMapping("last")
    @Operation(summary = "Получение  5-ти последних студента")
    public ResponseEntity<Collection<Student>> getLastFive() {
        return ResponseEntity.ok(service.getLastFive());
    }
    @GetMapping("name-by-a")
    @Operation(summary = "Получение имен на букву а ")
    public ResponseEntity<Collection<String>> getNamesByA() {
        return ResponseEntity.ok(service.getNamesByA());
    }
    @GetMapping("age/average-stream")
    @Operation(summary = "Получение среднего возраста студентов(stream)")
    public ResponseEntity<Double> getAverageAgeByStream() {
        return ResponseEntity.ok(service.getAverageAgeByStream());
    }
}
