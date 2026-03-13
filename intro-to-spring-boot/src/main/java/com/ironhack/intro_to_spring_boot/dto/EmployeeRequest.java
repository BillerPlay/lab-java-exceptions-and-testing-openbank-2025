package com.ironhack.intro_to_spring_boot.dto;

import com.ironhack.intro_to_spring_boot.enums.Status;
import jakarta.validation.constraints.NotEmpty;

public class EmployeeRequest {
    @NotEmpty(message = "EmployeeId can't be empty")
    private Long employeeId;

    @NotEmpty(message = "Dude has to have a name")
    private String name;

    @NotEmpty(message = "Department can't be empty")
    private  String department;

    @NotEmpty(message = "Status can't be empty")
    private String status;

    public EmployeeRequest(Long employeeId, String name, String department, String status) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmployeeStatus(Status employeeStatus) {
        this.status = employeeStatus.toString();
    }

    public Status getEmployeeStatus() {
        return Status.valueOf(status);
    }
}
