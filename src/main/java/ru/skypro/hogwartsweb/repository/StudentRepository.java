package ru.skypro.hogwartsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.hogwartsweb.model.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
