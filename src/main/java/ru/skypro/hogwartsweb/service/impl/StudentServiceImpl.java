package ru.skypro.hogwartsweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.hogwartsweb.exception.IncorrectException;
import ru.skypro.hogwartsweb.exception.NotFoundException;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.repository.StudentRepository;
import ru.skypro.hogwartsweb.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoker method add for students");
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Was invoker method remove for students");

        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoker method update for students");
        return studentRepository.save(student);

    }

    @Override
    public Student get(Long id) {
        logger.info("Was invoker method get for students");
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        }else {
            logger.error("Student not Found, id = " + id);
            throw new NotFoundException("Студент с " + id + " не существует");
        }
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoker method getAll for students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getByAge(Integer startAge,Integer engAge) {
        logger.info("Was invoker method getByAge for students");

        checkAge(startAge);
        checkAge(engAge);
        return studentRepository.findStudentByAgeBetween(startAge, engAge);
    }

    @Override
    public Integer getCount() {
        logger.info("Was invoker method getCount for students");
        return studentRepository.getCount();
    }

    @Override
    public Float getAverageAge() {
        logger.info("Was invoker method grtAverageAge for students");
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getLastFive() {
        logger.info("Was invoker method getLastFive for students");
        return studentRepository.getLastFive();
    }

    @Override
    public List<String> getNamesByA() {
        logger.info("Was invoker method getNamesByA for students");
        return studentRepository.findAll().stream()
                .filter(s -> s.getName().startsWith("A"))
                .map(n -> n.getName())
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeByStream() {
        logger.info("Was invoker method getAverageAgeByStream for students");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0f);
    }

    @Override
    public void printStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.size() >= 6) {
            students.subList(0,2).forEach(this::printStudentName);

            printStudents(students.subList(2,4));
            printStudents(students.subList(4,6));
        }
    }

    @Override
    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();

        if (students.size() >= 6) {
            students.subList(0,2).forEach(this::printStudentNameSync);

            printStudentsSync(students.subList(2,4));
            printStudentsSync(students.subList(4,6));
        }

    }

    private void printStudentName(Student student) {
        logger.info("Student: "+student.getId()+""+student.getName());
    }
    private synchronized void printStudentNameSync(Student student) {
        logger.info("Student: "+student.getId()+""+student.getName());
    }


    private void printStudents(List<Student> students) {
        new Thread(()->{
            students.forEach(this::printStudentName);
        }).start();
    }
    private void printStudentsSync(List<Student> students) {
        new Thread(()->{
            students.forEach(this::printStudentNameSync);
        }).start();
    }

    private void checkAge(Integer age) {
        logger.info("Was invoker method checkAge for students");
        if (age <= 10 || age >= 100) {
            logger.warn("Incorrect student age " + age);
            throw new IncorrectException("Требуеться указать корректный возраст");

        }
    }

}
