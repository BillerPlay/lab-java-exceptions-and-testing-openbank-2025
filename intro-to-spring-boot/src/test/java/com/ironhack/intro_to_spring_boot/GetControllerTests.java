package com.ironhack.intro_to_spring_boot;

import com.ironhack.intro_to_spring_boot.controllers.GetController;
import com.ironhack.intro_to_spring_boot.entities.Employee;
import com.ironhack.intro_to_spring_boot.entities.Patient;
import com.ironhack.intro_to_spring_boot.enums.Status;
import com.ironhack.intro_to_spring_boot.exception.ResourceNotFoundException;
import com.ironhack.intro_to_spring_boot.service.GetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(GetController.class)
class GetControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetService getService;


    @Test
    void shouldReturnAllEmployees() throws Exception {

        Employee em1 = new Employee(1L, "Diagnostics","Dr. House",  Status.ON_CALL);
        Employee em2 = new Employee(2L, "Surgeon","Dr. Horse",  Status.ON);
        when(getService.getAllEmployees()).thenReturn(List.of(em1,em2));

        mockMvc.perform(get("/api/hospital/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Dr. House"))
                .andExpect(jsonPath("$[0].department").value("Diagnostics"))
                .andExpect(jsonPath("$[1].employeeId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Dr. Horse"))
                .andExpect(jsonPath("$[1].department").value("Surgeon"));
    }


    @Test
    void shouldReturnEmployeeById() throws Exception {

        Employee em1 = new Employee(1L, "Diagnostics","Dr. House",  Status.ON_CALL);

        when(getService.getEmployeeById(1L)).thenReturn(em1);

        mockMvc.perform(get("/api/hospital/doctors/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1L))
                .andExpect(jsonPath("$.name").value("Dr. House"))
                .andExpect(jsonPath("$.department").value("Diagnostics"));
    }


    @Test
    void shouldReturnEmployeesByStatus() throws Exception {

        Employee em1 = new Employee(1L, "Diagnostics","Dr. House",  Status.ON_CALL);
        Employee em2 = new Employee(2L, "Surgeon","Dr. Horse",  Status.ON);

        when(getService.getEmployeesByStatus(Status.ON))
                .thenReturn(List.of(em2));

        mockMvc.perform(get("/api/hospital/doctors/status/ON"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(2L))
                .andExpect(jsonPath("$[0].name").value("Dr. Horse"))
                .andExpect(jsonPath("$[0].department").value("Surgeon"));
    }

    @Test
    void shouldNotReturnEmployeeById() throws Exception {
        when(getService.getEmployeeById(99L))
                .thenThrow(new ResourceNotFoundException("Resource is not found with id: " + 99L));

        mockMvc.perform(get("/api/hospital/doctors/id/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource is not found with id: 99"));
    }

    @Test
    void shouldReturnAllPatients() throws Exception {

        Patient p1 = new Patient(1L,"John","1990-01-01",1L);
        Patient p2 = new Patient(2L,"Mike","1995-05-05",2L);

        when(getService.getAllPatients()).thenReturn(List.of(p1,p2));

        mockMvc.perform(get("/api/hospital/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value(1L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$[0].admittedBy").value(1L))
                .andExpect(jsonPath("$[1].patientId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Mike"))
                .andExpect(jsonPath("$[1].dateOfBirth").value("1995-05-05"))
                .andExpect(jsonPath("$[1].admittedBy").value(2L));
    }


    @Test
    void shouldReturnPatientById() throws Exception {

        Patient p1 = new Patient(1L,"John","1990-01-01",1L);

        when(getService.getPatientById(1L)).thenReturn(p1);

        mockMvc.perform(get("/api/hospital/patients/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(1L))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$.admittedBy").value(1L));
    }

    @Test
    void shouldNotReturnPatientById() throws Exception {
        when(getService.getPatientById(99L)).thenThrow(new ResourceNotFoundException("Resource is not found with id: " + 99L));

        mockMvc.perform(get("/api/hospital/patients/id/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource is not found with id: 99"));

    }

    @Test
    void shouldReturnPatientsByDateRange() throws Exception {

        Patient p1 = new Patient(1L,"John","1990-01-01",1L);

        when(getService.getPatientsByDateRange("1980-01-01","2000-01-01"))
                .thenReturn(List.of(p1));

        mockMvc.perform(get("/api/hospital/patients/dataRange/1980-01-01&2000-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value(1L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$[0].admittedBy").value(1L));
    }

    @Test
    void shouldNotReturnPatientsByDateRange() throws Exception {
        when(getService.getPatientsByDateRange("1980-01-01","2000-01-01")).thenReturn(List.of());

        mockMvc.perform(get("/api/hospital/patients/dataRange/1980-01-01&2000-01-01"))
                .andExpect(status().isOk());

    }
    @Test
    void shouldReturnPatientsByDepartment() throws Exception {

        Patient p1 = new Patient(1L,"John","1990-01-01",1L);

        when(getService.getPatientsByDepartment("Diagnostics"))
                .thenReturn(List.of(p1));

        mockMvc.perform(get("/api/hospital/patients/department/Diagnostics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value(1L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$[0].admittedBy").value(1L));
    }


    @Test
    void shouldReturnPatientsByStatus() throws Exception {

        Patient p1 = new Patient(1L,"John","1990-01-01",1L);

        when(getService.getPatientsByStatus(Status.ON))
                .thenReturn(List.of(p1));

        mockMvc.perform(get("/api/hospital/patients/status/ON"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value(1L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$[0].admittedBy").value(1L));
    }
}