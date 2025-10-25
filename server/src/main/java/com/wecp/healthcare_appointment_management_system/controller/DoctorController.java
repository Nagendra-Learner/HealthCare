package com.wecp.healthcare_appointment_management_system.controller;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class DoctorController 
{
    @Autowired
    private DoctorService doctorService;


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException rtEx)
    {
        return new ResponseEntity<>(rtEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException sqlEx)
    {
        return new ResponseEntity<>(sqlEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/api/doctor/appointments")
    public ResponseEntity<List<Appointment>> viewAppointments(@RequestParam Long doctorId) 
    {
        List<Appointment> appointments = doctorService.getAppointments(doctorId);
        if(appointments.isEmpty())
        {
            throw new RuntimeException("No appointments for doctor with ID: " + doctorId);
        }
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/api/doctor/availability")
    public ResponseEntity<Doctor> manageAvailability(@RequestParam Long doctorId, @RequestParam String availability) 
    {
        Doctor doctor = doctorService.manageAvailability(doctorId, availability);
    
        return ResponseEntity.ok(doctor);

    }

    // @PostMapping("/api/doctor/register")
    // public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor)
    // {
    //     Doctor newDoctor = doctorService.registerDoctor(doctor);
    //     return new ResponseEntity<Doctor>(newDoctor, HttpStatus.CREATED);
    // }

    @GetMapping("/api/doctor")
    public ResponseEntity<List<Doctor>> getDoctors()
    {
        List<Doctor> doctors = doctorService.getDoctors();
        
        if(doctors.isEmpty())
        {
            throw new RuntimeException("No doctors found.");
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);

    }

    @GetMapping("/api/doctor/medicalrecords")
    public ResponseEntity<List<MedicalRecord>> viewMedicalRecords(@RequestParam Long doctorId) 
    {
        List<MedicalRecord> medicalRecords = doctorService.viewMedicalRecords(doctorId);

        if(medicalRecords.isEmpty())
        {
            throw new RuntimeException("No medical records for doctor with ID: " + doctorId);
        }
        return new ResponseEntity<>(medicalRecords,HttpStatus.OK);
    }

}
