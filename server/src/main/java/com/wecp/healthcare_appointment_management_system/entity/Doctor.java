package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Doctor extends User
{
   private String specialty;

   private String availability;

   @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
   @JsonIgnore
   private Set<Appointment> appointments = new HashSet<>();

   @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
   @JsonIgnore
   private Set<MedicalRecord> medicalRecords = new HashSet<>();
  
}
