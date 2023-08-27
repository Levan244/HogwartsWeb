package ru.skypro.hogwartsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.hogwartsweb.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Collection<Student> findStudentByAgeBetween(Integer startAge, Integer engAge);
}
