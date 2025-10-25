package com.wecp.healthcare_appointment_management_system.service;


import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    public Doctor manageAvailability(Long doctorId, String availability)
    {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new RuntimeException("Doctor with Id " + doctorId + " not found.");
        }
    
        doctor.setAvailability(availability);
        return doctorRepository.save(doctor);
        
    }

    public List<Appointment> getAppointments(Long doctorId)
    {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new RuntimeException("Doctor with Id " + doctorId + " not found.");
        }
     
        return appointmentService.getAppointmentsByDoctorId(doctorId);
        
    }

    public Doctor registerDoctor(Doctor doctor)
    {
        Doctor doctor1 = new Doctor();
		doctor1.setUsername("doctorUser");
		doctor1.setPassword("password");
		doctor1.setEmail("doctor@example.com");
		doctor1.setRole("DOCTOR");
		doctor1.setSpecialty("General Physician");
        return doctorRepository.save(doctor1);
    }

    public List<Doctor> getDoctors()
    {
        return doctorRepository.findAll();
    }

    public List<MedicalRecord> viewMedicalRecords(Long doctorId)
    {
        return medicalRecordService.viewMedicalRecordsByDoctorId(doctorId);
    }
}
