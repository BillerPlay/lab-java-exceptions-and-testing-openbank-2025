package com.ironhack.intro_to_spring_boot.controllers;

import com.ironhack.intro_to_spring_boot.database.AllEmployeesAndPatients;
import com.ironhack.intro_to_spring_boot.dto.EmployeeDTO;
import com.ironhack.intro_to_spring_boot.entities.Employee;
import com.ironhack.intro_to_spring_boot.entities.Patient;
import com.ironhack.intro_to_spring_boot.enums.Status;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/hospital")
public class PostController {

    private AllEmployeesAndPatients allEmployeesAndPatients;

    public PostController(AllEmployeesAndPatients allEmployeesAndPatients) {
        this.allEmployeesAndPatients = allEmployeesAndPatients;
    }

    // CREATE DOCTOR
    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createDoctor(@RequestBody @Valid EmployeeDTO employeeDTO) {

        Long id = employeeDTO.getEmployeeId();

        if (allEmployeesAndPatients.employees.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor already exists with this ID");
        }

        Employee newDoctor = new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getDepartment(),
                employeeDTO.getEmployeeStatus()
        );

        allEmployeesAndPatients.employees.put(id, newDoctor);

        return newDoctor;
    }

    // UPDATE DOCTOR STATUS
    @PatchMapping("/doctors/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDoctorStatus(
            @PathVariable Long id,
            @RequestBody Status status) {

        Employee doctor = allEmployeesAndPatients.employees.get(id);

        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        doctor.setStatus(status);
    }

    // UPDATE DOCTOR DEPARTMENT
    @PatchMapping("/doctors/{id}/department")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDoctorDepartment(
            @PathVariable Long id,
            @RequestBody String department) {

        Employee doctor = allEmployeesAndPatients.employees.get(id);

        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        doctor.setDepartment(department);
    }

    // CREATE PATIENT
    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient createPatient(@RequestBody @Valid Patient patient) {

        Long id = patient.getPatientId();

        if (allEmployeesAndPatients.patients.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient already exists");
        }

        allEmployeesAndPatients.patients.put(id, patient);

        return patient;
    }

    // UPDATE PATIENT
    @PatchMapping("/patients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePatient(
            @PathVariable Long id,
            @RequestBody Patient patientData) {

        Patient patient = allEmployeesAndPatients.patients.get(id);

        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }

        patient.setName(patientData.getName());
        patient.setDateOfBirth(patientData.getDateOfBirth());
        patient.setAdmittedBy(patientData.getAdmittedBy());
    }
}