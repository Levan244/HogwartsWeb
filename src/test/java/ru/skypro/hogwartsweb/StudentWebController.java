package ru.skypro.hogwartsweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.hogwartsweb.controller.StudentController;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.repository.StudentRepository;
import ru.skypro.hogwartsweb.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.hogwartsweb.Constant.*;

@WebMvcTest(controllers = StudentController.class)
public class StudentWebController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createStudent() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        JSONObject createStudentRq = new JSONObject();
        createStudentRq.put("name", MOCK_STUDENT_NAME);
        createStudentRq.put("color", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(createStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void getStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID)
                        .content(MOCK_STUDENT_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void updateStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        Constant.MOCK_FACULTY.setName(MOCK_STUDENT_NAME1);

        JSONObject updateStudentRq = new JSONObject();
        updateStudentRq.put("id", Constant.MOCK_STUDENT.getId());
        updateStudentRq.put("name", Constant.MOCK_STUDENT.getName());
        updateStudentRq.put("age", Constant.MOCK_STUDENT.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(Constant.MOCK_STUDENT);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student" )
                        .content(updateStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME1))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void removeStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(Constant.MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + MOCK_STUDENT_ID )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeUnknownStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + MOCK_STUDENT_ID )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllStudent() throws Exception {
        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all" )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
    }
    @Test
    public void getByAgeBetween() throws Exception {
        when(studentRepository.findStudentByAgeBetween(any(Integer.class),any(Integer.class)))
                .thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?startAge" + 18 + "&endAge=" + 25)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
    }

    @Test
    public void getFacultyByStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(Constant.MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/" + MOCK_STUDENT_ID)
                        .content(MOCK_STUDENT_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

