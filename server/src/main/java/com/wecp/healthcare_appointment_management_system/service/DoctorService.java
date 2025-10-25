package com.wecp.healthcare_appointment_management_system.service;


import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.User;
import com.wecp.healthcare_appointment_management_system.repository.AppointmentRepository;
import com.wecp.healthcare_appointment_management_system.repository.DoctorRepository;
import com.wecp.healthcare_appointment_management_system.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    public Doctor manageAvailability(Long doctorId, String availability)
    {
        Doctor d= doctorRepository.findById(doctorId).get();
        if(d != null)
        {
            d.setAvailability(availability);
            return doctorRepository.save(d);
        }
        return null;
    }

    public List<Appointment> getAppointments(Long doctorId)
    {
        Doctor d = doctorRepository.findById(doctorId).get();
        if(d != null)
        {
            return appointmentService.getAppointments();
        }
        return null;
    }

    public Doctor registerDoctor(Doctor doctor)
    {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getDoctors()
    {
        return doctorRepository.findAll();
    }
}
