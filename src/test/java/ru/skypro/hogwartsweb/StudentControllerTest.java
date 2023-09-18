package ru.skypro.hogwartsweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skypro.hogwartsweb.model.Student;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.hogwartsweb.Constant.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;



    @Test
    public void testCreateStudents() {
        ResponseEntity<Student> createStudentRs = createStudent(MOCK_STUDENT);

        assertThat(createStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createStudentRs.getBody().getName()).isEqualTo(MOCK_STUDENT.getName());
        assertThat(createStudentRs.getBody().getAge()).isEqualTo(MOCK_STUDENT.getAge());
    }

    @Test
    public void testGetStudent() {
        ResponseEntity<Student> createStudentRs = createStudent(MOCK_STUDENT);
        Student createStudent = createStudentRs.getBody();

        ResponseEntity<Student> studentGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + createStudent.getId(), Student.class);

        Student getStudent = studentGetRs.getBody();
        assertThat(getStudent.getId()).isEqualTo(createStudent.getId());
        assertThat(getStudent.getAge()).isEqualTo(createStudent.getAge());
        assertThat(getStudent.getName()).isEqualTo(createStudent.getName());


        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/age?startAge=18&anaAge=18", Student.class))
                .isNotNull();

        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/all", Student.class))
                .isNotNull();
    }

    @Test
    public void testRemoveStudent() {
        ResponseEntity<Student> createdStudentRs = createStudent(MOCK_STUDENT);
        Student createdStudent = createdStudentRs.getBody();

        testRestTemplate.delete("http://localhost:" + port + "/student/" + createdStudent.getId(), Student.class);

        ResponseEntity<Student> studentGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + createdStudent.getId(), Student.class);
        assertThat(MOCK_FACULTY.getId()).isNull();
    }

    @Test
    public void testChangeStudents() {
        ResponseEntity<Student> createdStudentRs = createStudent(MOCK_STUDENT);

        Student createdStudent = createdStudentRs.getBody();
        createdStudent.setName(MOCK_STUDENT_NAME1);

        ResponseEntity<Student> updateStudentRs = testRestTemplate.exchange(
                "/student",
                HttpMethod.PUT,
                new HttpEntity<>(createdStudent),
                Student.class
                );

        assertThat(updateStudentRs.getBody().getId()).isEqualTo(createdStudent.getId());
        assertThat(updateStudentRs.getBody().getAge()).isEqualTo(createdStudent.getAge());
        assertThat(updateStudentRs.getBody().getName()).isEqualTo(createdStudent.getName());
    }

    public ResponseEntity<Student> createStudent(Student student) {
        return testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
    }


}
