package ru.skypro.hogwartsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.hogwartsweb.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAgeBetween(Integer startAge, Integer engAge);

    @Query(nativeQuery = true, value = "select count(*) from students")
    Integer getCount();

    @Query(nativeQuery = true, value = "select evg(age) from students")
    Float getAverageAge();

    @Query(nativeQuery = true, value = "select *from students order by id desc limit 5")
    List<Student> getLastFive();

}
