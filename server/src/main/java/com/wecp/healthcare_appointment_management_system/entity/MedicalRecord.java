package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "medical_records")
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
   
}
