package ru.skypro.hogwartsweb.service;

import org.springframework.data.jpa.repository.Query;
import ru.skypro.hogwartsweb.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student add(Student student);

    Student remove(Long id);

    Student update(Student student);

    Student get(Long id);

    Collection<Student> getAll();

    Collection<Student> getByAge(Integer startAge,Integer engAge);

    Integer getCount();

    Float getAverageAge();

    List<Student> getLastFive();
}
