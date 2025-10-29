package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
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


   public String getSpecialty() {
      return specialty;
   }

   public void setSpecialty(String specialty) {
      this.specialty = specialty;
   }

   public String getAvailability() {
      return availability;
   }

   public void setAvailability(String availability) {
      this.availability = availability;
   }

   public Set<Appointment> getAppointments() {
      return appointments;
   }

   public void setAppointments(Set<Appointment> appointments) {
      this.appointments = appointments;
   }

   public Set<MedicalRecord> getMedicalRecords() {
      return medicalRecords;
   }

   public void setMedicalRecords(Set<MedicalRecord> medicalRecords) {
      this.medicalRecords = medicalRecords;
   }
   
}
