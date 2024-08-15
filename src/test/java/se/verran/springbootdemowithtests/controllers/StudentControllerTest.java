package se.verran.springbootdemowithtests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import se.verran.springbootdemowithtests.entities.Student;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String studentJson;
    private String expectedStudentJson;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        Student studentIn = new Student("Michael", "Granbäck", LocalDate.of(1983, 1, 29), "michael.granback@xlent.se");
        Student expectedStudentReturnData = new Student("Michael", "Granbäck", LocalDate.of(1983, 1, 29), "michael.granback@xlent.se");
        expectedStudentReturnData.setId(1);
        studentJson = objectMapper.writeValueAsString(studentIn);
        expectedStudentJson = objectMapper.writeValueAsString(expectedStudentReturnData);
    }

    @Test
    public void addStudentShouldReturnStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/edu/api/v1/addstudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedStudentJson));
    }
    @Test
    public void addStudentWithConflictingEmailShouldReturnStatusConflict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/edu/api/v1/addstudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedStudentJson));
        mockMvc.perform(MockMvcRequestBuilders.post("/edu/api/v1/addstudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
                )
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}