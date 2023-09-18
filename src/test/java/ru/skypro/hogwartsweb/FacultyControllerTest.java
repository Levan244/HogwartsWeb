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
import ru.skypro.hogwartsweb.model.Faculty;
import ru.skypro.hogwartsweb.model.Student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.hogwartsweb.Constant.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;



    @Test
    public void testCreateFaculty() {
        ResponseEntity<Faculty> createFacultyRs = createFaculty(MOCK_FACULTY);

        assertThat(createFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createFacultyRs.getBody().getName()).isEqualTo(MOCK_FACULTY.getName());
        assertThat(createFacultyRs.getBody().getColor()).isEqualTo(MOCK_FACULTY.getColor());
    }

    @Test
    public void testGetFaculty() {
        ResponseEntity<Faculty> createFacultyRs = createFaculty(MOCK_FACULTY);
        Faculty createFaculty = createFacultyRs.getBody();

        ResponseEntity<Faculty> facultyGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + createFaculty.getId(), Faculty.class);

        Faculty getFaculty = facultyGetRs.getBody();
        assertThat(getFaculty.getId()).isEqualTo(createFaculty.getId());
        assertThat(getFaculty.getColor()).isEqualTo(createFaculty.getColor());
        assertThat(getFaculty.getName()).isEqualTo(createFaculty.getName());


        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/find?name=" +MOCK_FACULTY_NAME + "&color=" + MOCK_FACULTY_COLOR, Faculty.class))
                .isNotNull();

        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/all", Faculty.class))
                .isNotNull();
    }

    @Test
    public void testRemoveFaculty() {
        ResponseEntity<Faculty> createdFacultyRs = createFaculty(MOCK_FACULTY);
        Faculty createdFaculty = createdFacultyRs.getBody();

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + createdFaculty.getId(), Faculty.class);

        ResponseEntity<Student> studentGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + createdFaculty.getId(), Student.class);
        assertThat(MOCK_FACULTY.getId()).isNull();
    }

    @Test
    public void testChangeFaculty() {
        ResponseEntity<Faculty> createdFacultyRs = createFaculty(MOCK_FACULTY);

        Faculty createdFaculty = createdFacultyRs.getBody();
        createdFaculty.setName(MOCK_FACULTY_NAME1);

        createdFacultyRs = testRestTemplate.exchange(
                "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(createdFaculty),
                Faculty.class
        );

        assertThat(createdFacultyRs.getBody().getId()).isEqualTo(createdFaculty.getId());
        assertThat(createdFacultyRs.getBody().getColor()).isEqualTo(createdFaculty.getColor());
        assertThat(createdFacultyRs.getBody().getName()).isEqualTo(createdFaculty.getName());
    }

    public ResponseEntity<Faculty> createFaculty(Faculty faculty) {
        return testRestTemplate.postForEntity("http://localhost:" + port + "/faculty",faculty, Faculty.class);
    }


}
