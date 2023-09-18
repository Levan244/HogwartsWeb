package ru.skypro.hogwartsweb.service;

import ru.skypro.hogwartsweb.model.Student;

import java.util.Collection;

public interface StudentService {
    Student add(Student student);

    Student remove(Long id);

    Student update(Student student);

    Student get(Long id);

    Collection<Student> getAll();

    Collection<Student> getByAge(Integer startAge,Integer engAge);
}
