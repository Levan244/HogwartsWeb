package ru.skypro.hogwartsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.hogwartsweb.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

}
