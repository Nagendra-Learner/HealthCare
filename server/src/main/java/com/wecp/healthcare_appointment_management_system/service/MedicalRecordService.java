package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
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

    public MedicalRecord getMedicalRecordById(Long medicalRecordId)
    {
        MedicalRecord medicalRecord = this.medicalRecordRepository.findById(medicalRecordId).orElse(null);

        if(medicalRecord == null)
        {
            throw new EntityNotFoundException("Medical Record with ID: " + medicalRecordId + " doesnot exists.");
        }

        return (medicalRecord);
    }

    public List<MedicalRecord> viewMedicalRecordsByDoctorId(Long doctorId)
    {
        Doctor doctor = this.doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new EntityNotFoundException("Doctor with ID: " + doctorId + " not found.");
        }
        
        return medicalRecordRepository.getMedicalRecordsByDoctorId(doctorId);
    }

    public List<MedicalRecord> viewMedicalRecordsByPatientId(Long patientId)
    {
        Patient patient = this.patientRepository.findById(patientId).orElse(null);

        if(patient == null)
        {
            throw new EntityNotFoundException("Patient with ID: " + patientId + " not found.");
        }

        return medicalRecordRepository.getMedicalRecordsByPatientId(patientId);
    }

    public MedicalRecord isMedicalRecordExsists(Long patientId, Long doctorId)
    {
        MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordByPatientIdDoctorId(patientId, doctorId);
        if(medicalRecord == null)
        {
            throw new EntityNotFoundException("Medical Record not found.");
        }

        return medicalRecord;
    }

    public MedicalRecord createMedicalReport(MedicalRecord medicalRecord)
    {
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalReport(MedicalRecord medicalRecord)
    {
        MedicalRecord newMedicalRecord = medicalRecordRepository.findById(medicalRecord.getId()).orElse(null);
        if(newMedicalRecord == null)
        {
            throw new EntityNotFoundException("Medical Record with ID: " + medicalRecord.getId() + " not found.");
        }

        newMedicalRecord.setDiagnosis(medicalRecord.getDiagnosis());
        newMedicalRecord.setNotes(medicalRecord.getNotes());
        newMedicalRecord.setPrescription(medicalRecord.getPrescription());
        newMedicalRecord.setRecordDate(medicalRecord.getRecordDate());
        
        return  medicalRecordRepository.save(newMedicalRecord);
        
    }

}
