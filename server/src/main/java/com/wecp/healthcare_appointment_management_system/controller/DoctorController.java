package com.wecp.healthcare_appointment_management_system.controller;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.exceptions.DuplicateEntityException;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController 
{
    @Autowired
    private DoctorService doctorService;


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException enfEx)
    {
        return new ResponseEntity<>(enfEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<String> handleDuplicateEntityException(DuplicateEntityException deEx)
    {
        return new ResponseEntity<>(deEx.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException sqlEx)
    {
        return new ResponseEntity<>(sqlEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> viewAppointments(@RequestParam Long doctorId) 
    {
        List<Appointment> appointments = doctorService.getAppointments(doctorId);
        if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/availability")
    public ResponseEntity<Doctor> manageAvailability(@RequestParam Long doctorId, @RequestParam String availability) 
    {
        Doctor doctor = doctorService.manageAvailability(doctorId, availability);
    
        return ResponseEntity.ok(doctor);

    }

    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor)
    {
        Doctor newDoctor = doctorService.registerDoctor(doctor);
        return new ResponseEntity<Doctor>(newDoctor, HttpStatus.CREATED);
    }

    @GetMapping("/medicalrecords")
    public ResponseEntity<List<MedicalRecord>> viewMedicalRecords(@RequestParam Long doctorId) 
    {
        List<MedicalRecord> medicalRecords = doctorService.viewMedicalRecords(doctorId);

        if(medicalRecords.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(medicalRecords,HttpStatus.OK);
    }

    @PostMapping("/medicalrecords/{patientId}")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@PathVariable Long patientId, @RequestBody MedicalRecord medicalRecord)
    {
        return new ResponseEntity<MedicalRecord>(this.doctorService.createMedicalRecord(patientId, medicalRecord), HttpStatus.CREATED);
    }

    @PutMapping("/medicalrecords/{patientId}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long patientId, @RequestBody MedicalRecord medicalRecord)
    {
        return new ResponseEntity<MedicalRecord>(this.doctorService.updateMedicalRecord(patientId, medicalRecord), HttpStatus.OK);
    }

}
