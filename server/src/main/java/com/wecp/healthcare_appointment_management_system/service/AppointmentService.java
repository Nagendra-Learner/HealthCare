package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.enums.AppointmentStatus;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
import com.wecp.healthcare_appointment_management_system.exceptions.TimeConflictException;
import com.wecp.healthcare_appointment_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
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
            throw new EntityNotFoundException("Patient with ID: " + patientId + " not found.");
        }
        if(doctor == null)
        {
            throw new EntityNotFoundException("Doctor with ID: " + doctorId + " not found.");
        }

        List<Appointment> existingAppointments = appointmentRepository.getAppointmentByDoctorId(doctorId);
        if(timeDto.getTime() == null)
        {
            throw new IllegalArgumentException("Invalid Time Format");
        }
        LocalDateTime appointmentTime = timeDto.getTime();

        for(Appointment existing: existingAppointments)
        {
            if(existing.getAppointmentTime().equals(appointmentTime))
            {
                throw new TimeConflictException("Doctor with ID: " + doctorId + " already has an appointment at " + appointmentTime);
            }
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus(AppointmentStatus.SCHEDULED.name());
        return this.appointmentRepository.save(appointment);

    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId)
    {
        Patient patient = this.patientRepository.findById(patientId).orElse(null);

        if(patient == null)
        {
            throw new EntityNotFoundException("Patient with ID: " + patientId + " not found.");
        }

        return this.appointmentRepository.getAppointmentByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId)
    {
        Doctor doctor = this.doctorRepository.findById(doctorId).orElse(null);

        if(doctor == null)
        {
            throw new EntityNotFoundException("Doctor with ID: " + doctorId + " not found.");
        }

        return this.appointmentRepository.getAppointmentByDoctorId(doctorId);
    }

    
    public Appointment rescheduleAppointment(Long appointmentId, TimeDto time) 
    {
         Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);

         if (appointment == null) 
         {
            throw new EntityNotFoundException("Appointment with ID: " + appointmentId + " not found.");
         }

        appointment.setAppointmentTime(time.getTime());
        appointment.setStatus(AppointmentStatus.RESCHEDULED.name());

        return appointmentRepository.save(appointment);
    }

}