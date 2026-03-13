package com.ironhack.intro_to_spring_boot.service;

import com.ironhack.intro_to_spring_boot.database.AllEmployeesAndPatients;
import com.ironhack.intro_to_spring_boot.entities.Employee;
import com.ironhack.intro_to_spring_boot.entities.Patient;
import com.ironhack.intro_to_spring_boot.enums.Status;
import com.ironhack.intro_to_spring_boot.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetService {

    private AllEmployeesAndPatients allEmployeesAndPatients;

    public GetService(AllEmployeesAndPatients allEmployeesAndPatients){
        this.allEmployeesAndPatients = allEmployeesAndPatients;
    }

    // DOCTORS

    public List<Employee> getAllEmployees(){
        return new ArrayList<>(allEmployeesAndPatients.employees.values());
    }

    public Employee getEmployeeById(Long id){
        Employee employee = allEmployeesAndPatients.employees.get(id);
        if(employee == null){
            throw new ResourceNotFoundException("Resource is not found with id: " + id);
        }
        return employee;
    }

    public List<Employee> getEmployeesByStatus(Status status){
        List<Employee> employeeList = new ArrayList<>();

        for(Employee employee : allEmployeesAndPatients.employees.values()){
            if (employee.getStatus() == status){
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    public List<Employee> getEmployeesByDepartment(String department){
        List<Employee> employeeList = new ArrayList<>();

        for(Employee employee : allEmployeesAndPatients.employees.values()){
            if (department.equals(employee.getDepartment())){
                employeeList.add(employee);
            }
        }

        return employeeList;
    }


    // PATIENTS

    public List<Patient> getAllPatients(){
        return new ArrayList<>(allEmployeesAndPatients.patients.values());
    }

    public Patient getPatientById(Long id){
        Patient patient = allEmployeesAndPatients.patients.get(id);
        if(patient == null){
            throw new ResourceNotFoundException("Resource is not found with id: " + id);
        }
        return patient;
    }

    public List<Patient> getPatientsByDateRange(String start, String end){

        List<Patient> patientList = new ArrayList<>();

        for (Patient patient : allEmployeesAndPatients.patients.values()) {

            if (patient.getDateOfBirth().compareTo(start) >= 0 &&
                    patient.getDateOfBirth().compareTo(end) <= 0) {

                patientList.add(patient);
            }
        }

        return patientList;
    }

    public List<Patient> getPatientsByDepartment(String department){

        List<Patient> patientList = new ArrayList<>();

        for (Patient patient : allEmployeesAndPatients.patients.values()) {
            Employee doctor = allEmployeesAndPatients.employees.get(patient.getAdmittedBy());

            if(doctor != null && doctor.getDepartment().equals(department)){
                patientList.add(patient);
            }
        }

        return patientList;
    }

    public List<Patient> getPatientsByStatus(Status status){

        List<Patient> patientList = new ArrayList<>();

        for (Patient patient : allEmployeesAndPatients.patients.values()) {
            Employee doctor = allEmployeesAndPatients.employees.get(patient.getAdmittedBy());

            if(doctor != null && doctor.getStatus() == status){
                patientList.add(patient);
            }
        }

        return patientList;
    }
}