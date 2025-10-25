package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService 
{
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<MedicalRecord> viewMedicalRecordsByDoctorId(Long doctorId)
    {
        Doctor doctor = this.doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new RuntimeException("Doctor with ID: " + doctorId + " not found.");
        }
        
        return medicalRecordRepository.getMedicalRecordsByDoctorId(doctorId);
    }

    public List<MedicalRecord> viewMedicalRecordsByPatientId(Long patientId)
    {
        Patient patient = this.patientRepository.findById(patientId).orElse(null);

        if(patient == null)
        {
            throw new RuntimeException("Patient with ID: " + patientId + " not found.");
        }
        return medicalRecordRepository.getMedicalRecordsByPatientId(patientId);
    }

    public Long createMedicalReport(MedicalRecord medicalRecord)
    {
        medicalRecordRepository.save(medicalRecord);
        return medicalRecord.getPatient().getId();
    }

    public void updateMedicalReport(MedicalRecord medicalRecord)
    {
        MedicalRecord newMedicalRecord = medicalRecordRepository.findById(medicalRecord.getId()).orElse(null);
        if(newMedicalRecord == null)
        {
            throw new RuntimeException("Medical Record with ID: " + medicalRecord.getId() + " not found.");
        }

        newMedicalRecord.setDiagnosis(medicalRecord.getDiagnosis());
        newMedicalRecord.setNotes(medicalRecord.getNotes());
        newMedicalRecord.setPrescription(medicalRecord.getPrescription());
        newMedicalRecord.setRecordDate(medicalRecord.getRecordDate());
        // newMedicalRecord.getPatient().setId(medicalRecord.getPatient().getId()));
        // newMedicalRecord.getDoctor().setId(medicalRecord.getDoctor().getId());
        medicalRecordRepository.save(newMedicalRecord);

    }

}
