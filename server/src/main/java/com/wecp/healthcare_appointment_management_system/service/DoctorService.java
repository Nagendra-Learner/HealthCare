package com.wecp.healthcare_appointment_management_system.service;


import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
import com.wecp.healthcare_appointment_management_system.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService 
{
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

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

    public List<Doctor> getAllDoctors()
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

}
