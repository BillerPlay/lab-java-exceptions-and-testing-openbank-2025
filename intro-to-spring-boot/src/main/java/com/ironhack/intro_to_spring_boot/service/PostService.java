package com.ironhack.intro_to_spring_boot.service;

import com.ironhack.intro_to_spring_boot.database.AllEmployeesAndPatients;
import com.ironhack.intro_to_spring_boot.dto.EmployeeRequest;
import com.ironhack.intro_to_spring_boot.entities.Employee;
import com.ironhack.intro_to_spring_boot.entities.Patient;
import com.ironhack.intro_to_spring_boot.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    private AllEmployeesAndPatients allEmployeesAndPatients;

    public PostService(AllEmployeesAndPatients allEmployeesAndPatients) {
        this.allEmployeesAndPatients = allEmployeesAndPatients;
    }

    // CREATE DOCTOR
    public Employee createEmployee(EmployeeRequest employeeRequest) {

        Long id = employeeRequest.getEmployeeId();

        if (allEmployeesAndPatients.employees.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Doctor already exists with this ID");
        }

        Employee newDoctor = new Employee(
                employeeRequest.getEmployeeId(),
                employeeRequest.getName(),
                employeeRequest.getDepartment(),
                employeeRequest.getEmployeeStatus()
        );

        allEmployeesAndPatients.employees.put(id, newDoctor);

        return newDoctor;
    }

    // UPDATE DOCTOR STATUS
    public void updateEmployeeStatus(Long id, Status status) {

        Employee doctor = allEmployeesAndPatients.employees.get(id);

        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        doctor.setStatus(status);
    }

    // UPDATE DOCTOR DEPARTMENT
    public void updateEmployeeDepartment(Long id, String department) {

        Employee doctor = allEmployeesAndPatients.employees.get(id);

        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        doctor.setDepartment(department);
    }

    // CREATE PATIENT
    public Patient createPatient(Patient patient) {

        Long id = patient.getPatientId();

        if (allEmployeesAndPatients.patients.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Patient already exists");
        }

        allEmployeesAndPatients.patients.put(id, patient);

        return patient;
    }

    // UPDATE PATIENT
    public void updatePatient(Long id, Patient patientData) {

        Patient patient = allEmployeesAndPatients.patients.get(id);

        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }

        patient.setName(patientData.getName());
        patient.setDateOfBirth(patientData.getDateOfBirth());
        patient.setAdmittedBy(patientData.getAdmittedBy());
    }
}