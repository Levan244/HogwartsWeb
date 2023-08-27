package ru.skypro.hogwartsweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.skypro.hogwartsweb.exception.IncorrectException;
import ru.skypro.hogwartsweb.exception.NotFoundException;
import ru.skypro.hogwartsweb.model.Faculty;
import ru.skypro.hogwartsweb.repository.FacultyRepository;
import ru.skypro.hogwartsweb.service.FacultyService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
       return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new NotFoundException("Факультет с " + id + " не существует");
        }
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getByColorOrNAme(String color,String name) {
        if (!StringUtils.hasText(color) && !StringUtils.hasText(name)) {
            throw new IncorrectException("Требуеться указать корректный цвет или имя для поиска");
        }

        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(color, name);
    }
}
