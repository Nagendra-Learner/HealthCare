package com.wecp.healthcare_appointment_management_system.service;


import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.exceptions.DuplicateEntityException;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
import com.wecp.healthcare_appointment_management_system.exceptions.InvalidAssociationException;
import com.wecp.healthcare_appointment_management_system.repository.DoctorRepository;
import com.wecp.healthcare_appointment_management_system.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    public Doctor manageAvailability(Long doctorId, String availability)
    {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new EntityNotFoundException("Doctor with Id " + doctorId + " not found.");
        }
    
        doctor.setAvailability(availability);
        return doctorRepository.save(doctor);
        
    }

    public List<Doctor> getDoctors()
    {
        return this.doctorRepository.findAll();
    }

    public List<Appointment> getAppointments(Long doctorId)
    {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new EntityNotFoundException("Doctor with Id " + doctorId + " not found.");
        }
     
        return appointmentService.getAppointmentsByDoctorId(doctorId);
        
    }

    public Doctor registerDoctor(Doctor doctor)
    {
        Doctor retrievedDoctor = this.doctorRepository.findByUsername(doctor.getUsername()).orElse(null);

        if(retrievedDoctor != null)
        {
            if(retrievedDoctor.getUsername().equals(doctor.getUsername()))
            {
                throw new DuplicateEntityException("Username already exists.");
            }
            if(retrievedDoctor.getEmail().equals(doctor.getEmail()))
            {
                throw new DuplicateEntityException("Email already exists.");
            }
        }

        return doctorRepository.save(doctor);
        
    }

    public List<MedicalRecord> viewMedicalRecords(Long doctorId)
    {
        return medicalRecordService.viewMedicalRecordsByDoctorId(doctorId);
    }

    public MedicalRecord createMedicalRecord(Long patientId, MedicalRecord medicalRecord)
    {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null)
        {
            throw new EntityNotFoundException("Patient with Id " + patientId + " not found.");
        }

        medicalRecord.setPatient(patient);
        return medicalRecordService.createMedicalReport(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(Long patientId, MedicalRecord medicalRecord)
    {
        
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null)
        {
            throw new EntityNotFoundException("Patient with Id " + patientId + " not found.");
        }
        MedicalRecord newMedicalRecord = this.medicalRecordService.getMedicalRecordById(medicalRecord.getId());
        if(newMedicalRecord == null)
        {
            throw new EntityNotFoundException("Medical Record with Id " + medicalRecord.getId() + " not found.");
        }

        if(patientId != newMedicalRecord.getPatient().getId())
        {
            throw new InvalidAssociationException("Medical Record with Id " + medicalRecord.getId() + " is not associated with patient Id " + patientId);
        }

        return medicalRecordService.updateMedicalReport(medicalRecord);
    }
}
