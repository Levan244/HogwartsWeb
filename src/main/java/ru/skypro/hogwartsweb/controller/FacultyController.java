package ru.skypro.hogwartsweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.hogwartsweb.model.Faculty;
import ru.skypro.hogwartsweb.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание факульта")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty addedFaculty = service.add(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @PostMapping("{id}")
    @Operation(summary = "Получение факульта")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = service.get(id);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    @Operation(summary = "Изменение факульта")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty = service.update(faculty);
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факульта")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        Faculty faculty = service.remove(id);
        return ResponseEntity.ok(faculty);
    }

    @PostMapping("all")
    @Operation(summary = "Получение всех факультетов")
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("collor")
    @Operation(summary = "получение факультетов по цвету")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@RequestParam String color) {
        return ResponseEntity.ok(service.getByColor(color));
    }
}

