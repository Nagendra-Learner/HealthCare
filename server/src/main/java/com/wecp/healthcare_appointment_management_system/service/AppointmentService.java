package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService 
{
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Appointment> getAppointments()
    {
        return this.appointmentRepository.findAll();
    }

    public Appointment scheduleAppointments(Long patientId, Long doctorId, TimeDto timeDto)
    {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if(patient == null)
        {
            throw new RuntimeException("Patient with ID: " + patientId + " not found.");
        }
        if(doctor == null)
        {
            throw new RuntimeException("Doctor with ID: " + doctorId + " not found.");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(Date.from(timeDto.getTime().toInstant()));
        appointment.setStatus("Scheduled");
        return this.appointmentRepository.save(appointment);

    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId)
    {
        Patient patient = this.patientRepository.findById(patientId).orElse(null);

        if(patient == null)
        {
            throw new RuntimeException("Patient with ID: " + patientId + " not found.");
        }

        return this.appointmentRepository.getAppointmentByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId)
    {
        Doctor doctor = this.doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new RuntimeException("Doctor with ID: " + doctorId + " not found.");
        }
        return this.appointmentRepository.getAppointmentByDoctorId(doctorId);

    }

    
    public Appointment rescheduleAppointment(Long appointmentId, Date time) 
    {
         Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
         if (appointment == null) 
         {
            throw new RuntimeException("Appointment with ID: " + appointmentId + " not found.");
         }

        // LocalDateTime newTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        appointment.setAppointmentTime(time);
        appointment.setStatus("RESCHEDULED");
        return appointmentRepository.save(appointment);
    }

}