package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patient")
public class Patient extends User 
{   // implement patient entity
   
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<MedicalRecord> medicalRecords = new HashSet<>();

    public Set<MedicalRecord> getMedicalRecords() 
    {
        return medicalRecords;
    }

    public void setMedicalRecords(Set<MedicalRecord> medicalRecords) 
    {
        this.medicalRecords = medicalRecords;
    }

    public Set<Appointment> getAppointments() 
    {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) 
    {
        this.appointments = appointments;
    }

    @Override
    public String toString() 
    {   
        return "Patient [medicalRecords = " + medicalRecords + ", appointments = " + appointments + "]";
    }
    
}
