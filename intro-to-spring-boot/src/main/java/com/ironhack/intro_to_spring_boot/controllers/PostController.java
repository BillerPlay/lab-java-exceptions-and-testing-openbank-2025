package com.ironhack.intro_to_spring_boot.controllers;

import com.ironhack.intro_to_spring_boot.dto.EmployeeRequest;
import com.ironhack.intro_to_spring_boot.entities.Employee;
import com.ironhack.intro_to_spring_boot.entities.Patient;
import com.ironhack.intro_to_spring_boot.enums.Status;
import com.ironhack.intro_to_spring_boot.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return postService.createEmployee(employeeRequest);
    }

    @PatchMapping("/doctors/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployeeStatus(
            @PathVariable Long id,
            @RequestBody Status status) {

        postService.updateEmployeeStatus(id, status);
    }

    @PatchMapping("/doctors/{id}/department")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployeeDepartment(
            @PathVariable Long id,
            @RequestBody String department) {

        postService.updateEmployeeDepartment(id, department);
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient createPatient(@RequestBody @Valid Patient patient) {
        return postService.createPatient(patient);
    }

    @PatchMapping("/patients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePatient(
            @PathVariable Long id,
            @RequestBody Patient patientData) {

        postService.updatePatient(id, patientData);
    }
}