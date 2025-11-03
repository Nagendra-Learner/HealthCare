package com.wecp.healthcare_appointment_management_system.controller;

import java.util.List;

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
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.service.MedicalRecordService;

@RestController
@RequestMapping("/api/medicalRecord")
public class MedicalRecordController 
{
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> fetchAllMedicalRecords()
    {
        List<MedicalRecord> medicalRecords = this.medicalRecordService.getAllMedicalRecords();

        if(medicalRecords.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }  

    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<MedicalRecord> fetchMedicalRecordById(@PathVariable Long medicalRecordId)
    {
        return new ResponseEntity<>(this.medicalRecordService.getMedicalRecordById(medicalRecordId), HttpStatus.OK);
    }

    @GetMapping("/patientAndDoctor")
    public ResponseEntity<MedicalRecord> isMedicalRecordExsists(@RequestParam Long patientId, @RequestParam Long doctorId)
    {
        return new ResponseEntity<>(this.medicalRecordService.isMedicalRecordExsists(patientId, doctorId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestParam Long appointmentId, @RequestBody MedicalRecord medicalRecord)
    {
        return new ResponseEntity<>(medicalRecordService.createMedicalRecord(patientId, doctorId, appointmentId, medicalRecord), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestParam Long medicalRecordId, @RequestBody MedicalRecord medicalRecord) 
    {
        return new ResponseEntity<>(this.medicalRecordService.updateMedicalRecord(medicalRecordId, medicalRecord), HttpStatus.OK);
    }
    

}
