package com.ironhack.intro_to_spring_boot;

import com.ironhack.intro_to_spring_boot.controllers.PostController;
import com.ironhack.intro_to_spring_boot.dto.EmployeeRequest;
import com.ironhack.intro_to_spring_boot.entities.Employee;
import com.ironhack.intro_to_spring_boot.entities.Patient;
import com.ironhack.intro_to_spring_boot.enums.Status;
import com.ironhack.intro_to_spring_boot.exception.ResourceNotFoundException;
import com.ironhack.intro_to_spring_boot.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;


    @Test
    void shouldCreateEmployee() throws Exception {
        Employee employee = new Employee(1L, "Surgery", "Dr. Strange", Status.ON);
        EmployeeRequest employeeRequest = new EmployeeRequest(1L, "Surgery", "Dr. Strange", "ON");
        String request = """
        {
            "employeeId": "New Book",
            "name": "Author Name",
            "department": 25.99,
            "status": ON
        }
        """;
        when(postService.createEmployee(employeeRequest)).thenReturn(employee);

        mockMvc.perform(post("/api/hospital/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(1L))
                .andExpect(jsonPath("$.name").value("Dr. Strange"))
                .andExpect(jsonPath("$.department").value("Surgery"))
                .andExpect(jsonPath("$.status").value("ON"));
    }

    @Test
    void shouldNotCreateEmployee_InvalidRequest_Returns400() throws Exception {
        // Пустое имя — валидация упадёт до вызова сервиса
        String request = """
                {
                    "employeeId": 1,
                    "department": "Surgery",
                    "name": "",
                    "status": "ON"
                }
                """;

        mockMvc.perform(post("/api/hospital/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    void shouldUpdateEmployeeStatus_Returns204() throws Exception {
        doNothing().when(postService).updateEmployeeStatus(eq(1L), any());

        mockMvc.perform(patch("/api/hospital/doctors/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"ON_CALL\""))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateEmployeeStatus_NotFound_Returns404() throws Exception {
        doThrow(new ResourceNotFoundException("Doctor not found"))
                .when(postService).updateEmployeeStatus(eq(999L), any());

        mockMvc.perform(patch("/api/hospital/doctors/999/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"ON_CALL\""))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Doctor not found"));
    }

    @Test
    void shouldUpdateEmployeeDepartment_Returns204() throws Exception {
        doNothing().when(postService).updateEmployeeDepartment(eq(1L), any());

        mockMvc.perform(patch("/api/hospital/doctors/1/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Diagnostics\""))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateEmployeeDepartment_NotFound_Returns404() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"))
                .when(postService).updateEmployeeDepartment(eq(999L), any());

        mockMvc.perform(patch("/api/hospital/doctors/999/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Diagnostics\""))
                .andExpect(status().isNotFound());
    }

    // ==================== PATIENT TESTS ====================

    @Test
    void shouldCreatePatient_Returns201() throws Exception {
        Patient patient = new Patient(1L, "John Doe", "Dr. Strange", 1L);
        when(postService.createPatient(any())).thenReturn(patient);

        String request = """
                {
                    "patientId": 1,
                    "name": "John Doe",
                    "admittedBy": 1,
                    "dateOfBirth": "2000-01-01"
                }
                """;

        mockMvc.perform(post("/api/hospital/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientId").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void shouldNotCreatePatient_InvalidRequest_Returns400() throws Exception {
        // name пустое — валидация падает до сервиса
        String request = """
                {
                    "patientId": 1,
                    "name": "",
                    "admittedBy": 1,
                    "dateOfBirth": "2000-01-01"
                }
                """;

        mockMvc.perform(post("/api/hospital/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void shouldUpdatePatient_Returns204() throws Exception {
        doNothing().when(postService).updatePatient(eq(1L), any());

        String request = """
                {
                    "patientId": 1,
                    "name": "Jane Smith",
                    "admittedBy": 2,
                    "dateOfBirth": "1990-05-15"
                }
                """;

        mockMvc.perform(patch("/api/hospital/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdatePatient_NotFound_Returns404() throws Exception {
        doThrow(new ResourceNotFoundException("Patient not found with id: 999"))
                .when(postService).updatePatient(eq(999L), any());

        String request = """
                {
                    "patientId": 999,
                    "name": "Ghost",
                    "admittedBy": 1,
                    "dateOfBirth": "1990-01-01"
                }
                """;

        mockMvc.perform(patch("/api/hospital/patients/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }
}

