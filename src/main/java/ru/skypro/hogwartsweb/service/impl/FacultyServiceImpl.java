package ru.skypro.hogwartsweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.skypro.hogwartsweb.exception.IncorrectException;
import ru.skypro.hogwartsweb.exception.NotFoundException;
import ru.skypro.hogwartsweb.model.Faculty;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.repository.FacultyRepository;
import ru.skypro.hogwartsweb.repository.StudentRepository;
import ru.skypro.hogwartsweb.service.FacultyService;
import ru.skypro.hogwartsweb.service.StudentService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoker method add for faculties");

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Was invoker method remove for faculties");
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoker method update for faculties");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        logger.info("Was invoker method get for faculties");
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            logger.error("Faculties not Found, id = " + id);
            throw new NotFoundException("Факультет с " + id + " не существует");
        }
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Was invoker method getAll for faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getByColorOrNAme(String color,String name) {
        logger.info("Was invoker method getByColorOrNAme for faculties");
        if (!StringUtils.hasText(color) && !StringUtils.hasText(name)) {
            logger.warn("Incorrect faculty color " + color);
            throw new IncorrectException("Требуеться указать корректный цвет или имя для поиска");
        }

        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(color, name);
    }

    @Override
    public String getLongestFacultyName() {
        logger.info("Was invoker method getLongestFacultyName for faculties");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

}
