package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.print.Doc;

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

        Appointment appointment = new Appointment();
        if(patient != null && doctor != null)
        {
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setAppointmentTime(timeDto.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            appointment.setStatus("SCHEDULED");
        }

        return this.appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentByPatientId(Long patientId)
    {
        return this.appointmentRepository.getAppointmentByPatientId(patientId);
    }

    public List<Appointment> getAppointmentByDoctorId(Long doctorId)
    {
        return this.appointmentRepository.getAppointmentByDoctorId(doctorId);
    }

    
    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newTime) 
    {
         Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
         if (appointment == null) 
            return null;

        appointment.setAppointmentTime(newTime);
        appointment.setStatus("RESCHEDULED");

        return appointmentRepository.save(appointment);

    }
}