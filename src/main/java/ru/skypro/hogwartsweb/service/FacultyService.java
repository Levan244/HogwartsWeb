package ru.skypro.hogwartsweb.service;

import ru.skypro.hogwartsweb.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty add(Faculty faculty);

    Faculty remove(Long id);

    Faculty update(Faculty faculty);

    Faculty get(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getByColorOrNAme(String color,String name);

}
