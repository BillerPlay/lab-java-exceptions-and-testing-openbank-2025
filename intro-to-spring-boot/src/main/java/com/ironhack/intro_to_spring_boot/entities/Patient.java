package com.ironhack.intro_to_spring_boot.entities;



import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.annotation.JsonSerialize;
public class Patient {
    @JsonSerialize
    private Long patientId;
    @NotBlank
    private String name;
    @JsonSerialize
    private String dateOfBirth;
    @JsonSerialize
    private Long admittedBy;

    public Patient(Long patientId, String name, String dateOfBirth, Long admittedBy) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.admittedBy = admittedBy;
    }

    public Long getPatientId() {
        return patientId;
    }
    public String getName() {
        return name;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public Long getAdmittedBy() {
        return admittedBy;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAdmittedBy(Long admittedBy) {
        this.admittedBy = admittedBy;
    }
}
