package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="patient")
public class Patient extends User {
    // implement patient entity
    @OneToOne
    User user;
    @OneToMany(mappedBy="patient",cascade=CascadeType.ALL)
    Set<MedicalRecord> medicalRecords;
    @OneToMany(mappedBy = "patient",cascade=CascadeType.ALL)
    Set<Appointment> appointments;
    public Patient(){
        
    }
    public Patient(Long id,String username,String password,String email,User user,Set<MedicalRecord> medicalRecords,Set<Appointment> appointments){
        super(id,username,password,email,"PATIENT");
        this.user=user;
        this.medicalRecords=medicalRecords;
        this.appointments=appointments;
    }
    public Set<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
    public void setMedicalRecords(Set<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
    public Set<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
    @Override
    public String toString() {
        return "Patient [medicalRecords=" + medicalRecords + ", appointments=" + appointments + "]";
    }
    

}
