package com.wecp.healthcare_appointment_management_system.controller;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;
import com.wecp.healthcare_appointment_management_system.service.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController 
{
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MedicalRecordService medicalRecordService;

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

    @PutMapping("/availability")
    public ResponseEntity<Doctor> manageAvailability(@RequestParam Long doctorId, @RequestParam String availability) 
    {
        Doctor doctor = doctorService.manageAvailability(doctorId, availability);
    
        return ResponseEntity.ok(doctor);

    }

    // @GetMapping("/medicalrecords")
    // public ResponseEntity<List<MedicalRecord>> viewMedicalRecords(@RequestParam Long doctorId) 
    // {
    //     List<MedicalRecord> medicalRecords = doctorService.viewMedicalRecords(doctorId);

    //     if(medicalRecords.isEmpty())
    //     {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    // }

    @GetMapping("/medicalrecords")
    public ResponseEntity<MedicalRecord> viewMedicalRecordById(@RequestParam Long medicalRecordId) 
    {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(medicalRecordId);

        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    // @GetMapping("/medicalrecords/{patientId}")
    // public ResponseEntity<MedicalRecord> viewMedicalRecordByPatientId(@PathVariable Long patientId) 
    // {
    //     MedicalRecord medicalRecord = medicalRecordService.viewMedicalRecordsByPatientId(patientId);

    //     return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    // }

    // @GetMapping("/medicalrecords/{doctorId}")
    // public ResponseEntity<MedicalRecord> viewMedicalRecordByDoctorId(@PathVariable Long doctorId) 
    // {
    //     MedicalRecord medicalRecord = medicalRecordService.viewMedicalRecordsByDoctorId(doctorId);

    //     return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    // }

    @GetMapping("/medicalrecords/{patientId}/{doctorId}")
    public ResponseEntity<MedicalRecord> viewMedicalRecordByPatientIdDoctorId(@PathVariable Long patientId, @PathVariable Long doctorId) 
    {
        MedicalRecord medicalRecord = medicalRecordService.isMedicalRecordExsists(patientId, doctorId);

        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }



    @PostMapping("/medicalrecords/{patientId}/{doctorId}/{appointmentId}")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@PathVariable Long patientId, @PathVariable Long doctorId, @PathVariable Long appointmentId, @RequestBody MedicalRecord medicalRecord)
    {
        return new ResponseEntity<MedicalRecord>(this.doctorService.createMedicalRecord(patientId, doctorId, appointmentId, medicalRecord), HttpStatus.CREATED);
    }

    @PutMapping("/medicalrecords/{medicalRecordId}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long medicalRecordId, @RequestBody MedicalRecord medicalRecord)
    {
        return new ResponseEntity<MedicalRecord>(this.doctorService.updateMedicalRecord(medicalRecordId, medicalRecord), HttpStatus.OK);
    }

}
