package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Appointment 
{ 
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
        
}
