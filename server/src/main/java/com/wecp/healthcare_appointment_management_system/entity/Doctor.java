package com.wecp.healthcare_appointment_management_system.entity;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Doctor extends User
{
   private String speciality;
   private String availability;

   @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
   private Set<Appointment> appointments = new HashSet<>();

   @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
   private Set<MedicalRecord> medicalRecords = new HashSet<>();

   public Doctor() {}

   public String getSpeciality() {
      return speciality;
   }

   public void setSpeciality(String speciality) {
      this.speciality = speciality;
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
