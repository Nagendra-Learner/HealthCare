package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
import com.wecp.healthcare_appointment_management_system.repository.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService 
{
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<MedicalRecord> getAllMedicalRecords()
    {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecordById(Long medicalRecordId)
    {
        MedicalRecord medicalRecord = this.medicalRecordRepository.findById(medicalRecordId).orElse(null);

        if(medicalRecord == null)
        {
            throw new EntityNotFoundException("Medical Record with ID: " + medicalRecordId + " doesnot exists.");
        }

        return (medicalRecord);
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

    public MedicalRecord createMedicalRecord(Long patientId, Long doctorId, Long appointmentId, MedicalRecord medicalRecord)
    {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null)
        {
            throw new EntityNotFoundException("Patient with Id " + patientId + " not found.");
        }

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if(doctor == null)
        {
            throw new EntityNotFoundException("Doctor with Id " + doctorId + " not found.");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);

        if(appointment == null)
        {
            throw new EntityNotFoundException("Appointment with Id " + appointmentId + " not found.");
        }

        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setAppointment(appointment);

        appointment.setMedicalRecord(medicalRecord);

        //appointmentRepository.save(appointment);

        return medicalRecordRepository.save(medicalRecord);
    }
    
    public MedicalRecord updateMedicalRecord(Long medicalRecordId, MedicalRecord medicalRecord) 
    {
        MedicalRecord retrieved = medicalRecordRepository.findById(medicalRecordId).orElse(null);

        if(medicalRecord == null)
        {
            throw new EntityNotFoundException("Medical Record with Id " + medicalRecordId + " not found.");
        }

        retrieved.setDiagnosis(medicalRecord.getDiagnosis());
        retrieved.setNotes(medicalRecord.getNotes());
        retrieved.setPrescription(medicalRecord.getPrescription());
        retrieved.setRecordDate(medicalRecord.getRecordDate());

        return medicalRecordRepository.save(retrieved);
    }

}
