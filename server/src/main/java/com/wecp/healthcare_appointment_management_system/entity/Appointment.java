package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment 
{   // implement appointment entity
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_id")
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    Patient patient;

    @ManyToOne(cascade = CascadeType.MERGE)
    Doctor doctor;

    LocalDateTime appointmentTime;
    String status;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
