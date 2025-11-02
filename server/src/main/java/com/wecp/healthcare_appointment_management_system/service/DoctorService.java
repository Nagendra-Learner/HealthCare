package com.wecp.healthcare_appointment_management_system.service;


import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
import com.wecp.healthcare_appointment_management_system.exceptions.InvalidAssociationException;
import com.wecp.healthcare_appointment_management_system.repository.AppointmentRepository;
import com.wecp.healthcare_appointment_management_system.repository.DoctorRepository;
import com.wecp.healthcare_appointment_management_system.repository.MedicalRecordRepository;
import com.wecp.healthcare_appointment_management_system.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService 
{
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;


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

    public List<MedicalRecord> viewMedicalRecords(Long doctorId)
    {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new EntityNotFoundException("Doctor with Id " + doctorId + " not found.");
        }

        return medicalRecordService.viewMedicalRecordsByDoctorId(doctorId);
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
        return medicalRecordService.createMedicalReport(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(Long medicalRecordId, MedicalRecord medicalRecord)
    {
        MedicalRecord retrievedmedicalRecord = medicalRecordRepository.findById(medicalRecordId).orElse(null);
        if(retrievedmedicalRecord == null)
        {
            throw new EntityNotFoundException("Medical Record with Id " + medicalRecordId + " not found.");
        }

        if(retrievedmedicalRecord.getPatient().getId() != medicalRecord.getPatient().getId())
        {
            throw new InvalidAssociationException("Medical Record with Id " + medicalRecord.getId() + " is not associated with patient Id " + retrievedmedicalRecord.getPatient().getId());
        }

        return medicalRecordService.updateMedicalReport(retrievedmedicalRecord);
    }
}
