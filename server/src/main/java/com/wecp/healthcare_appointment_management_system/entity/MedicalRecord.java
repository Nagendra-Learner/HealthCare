package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="medical_records")
public class MedicalRecord 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Patient patient;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Doctor doctor;

    private String diagnosis;
    private String prescription;
    private String notes;
    private LocalDateTime recordDate;

    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Patient getPatient() 
    {
        return patient;
    }

    public void setPatient(Patient patient) 
    {
        this.patient = patient;
    }

    public Doctor getDoctor() 
    {
        return doctor;
    }

    public void setDoctor(Doctor doctor) 
    {
        this.doctor = doctor;
    }

    public String getDiagnosis() 
    {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) 
    {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() 
    {
        return prescription;
    }

    public void setPrescription(String prescription) 
    {
        this.prescription = prescription;
    }

    public String getNotes() 
    {
        return notes;
    }

    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public LocalDateTime getRecordDate() 
    {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) 
    {
        this.recordDate = recordDate;
    }
   
}
