package ru.skypro.hogwartsweb.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.hogwartsweb.exception.IncorrectException;
import ru.skypro.hogwartsweb.exception.NotFoundException;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.repository.StudentRepository;
import ru.skypro.hogwartsweb.service.StudentService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        return studentRepository.save(student);

    }

    @Override
    public Student get(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        }else {
            throw new NotFoundException("Студент с " + id + " не существует");
        }
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getByAge(Integer age) {
        if (age <= 10 || age >= 100) {
            throw new IncorrectException("Требуеться указать корректный возраст");
        }

        return studentRepository.findAll().stream()
                .filter(s->s.getAge().equals(age))
                .collect(Collectors.toList());
    }

}
