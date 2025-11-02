package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment 
{   // implement appointment entity
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Patient patient;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Doctor doctor;

    private LocalDateTime appointmentTime;

    private String status;

    @JsonManagedReference
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    MedicalRecord medicalRecord;
        
}
