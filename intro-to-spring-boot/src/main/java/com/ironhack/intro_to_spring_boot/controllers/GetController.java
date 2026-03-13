package com.ironhack.intro_to_spring_boot.controllers;

import com.ironhack.intro_to_spring_boot.entities.Employee;
import com.ironhack.intro_to_spring_boot.entities.Patient;
import com.ironhack.intro_to_spring_boot.enums.Status;
import com.ironhack.intro_to_spring_boot.service.GetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class GetController {

    private GetService getService;

    public GetController(GetService getService){
        this.getService = getService;
    }

    @GetMapping("/doctors")
    public List<Employee> getAllEmployees(){
        return getService.getAllEmployees();
    }

    @GetMapping("/doctors/id/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return getService.getEmployeeById(id);
    }

    @GetMapping("/doctors/status/{status}")
    public List<Employee> getEmployeesByStatus(@PathVariable Status status){
        return getService.getEmployeesByStatus(status);
    }

    @GetMapping("/doctors/department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department){
        return getService.getEmployeesByDepartment(department);
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients(){
        return getService.getAllPatients();
    }

    @GetMapping("/patients/id/{id}")
    public Patient getPatientById(@PathVariable Long id){
        return getService.getPatientById(id);
    }

    @GetMapping("/patients/dataRange/{start}&{end}")
    public List<Patient> getPatientsByDateRange(
            @PathVariable String start,
            @PathVariable String end){

        return getService.getPatientsByDateRange(start, end);
    }

    @GetMapping("/patients/department/{department}")
    public List<Patient> getPatientsByDepartment(@PathVariable String department){
        return getService.getPatientsByDepartment(department);
    }

    @GetMapping("/patients/status/{status}")
    public List<Patient> getPatientsByStatus(@PathVariable Status status){
        return getService.getPatientsByStatus(status);
    }
}