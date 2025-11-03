package com.wecp.healthcare_appointment_management_system.controller;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.service.AppointmentService;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController 
{
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    @GetMapping("/appointment")
    public ResponseEntity<List<Appointment>> fetchAppointments(@RequestParam Long patientId) 
    {

        List<Appointment> appointments = this.appointmentService.getAppointmentsByPatientId(patientId);

        if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Appointment>>(appointments,HttpStatus.OK);

    }

    @GetMapping("/available")
    public ResponseEntity<List<Doctor>> fetchAvailableDoctors() 
    {

        List<Doctor> doctors = doctorService.getAllDoctors();

        if(doctors.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Doctor> updatedDoctors = new ArrayList<>();

        for(Doctor doctor: doctors)
        {
            if(doctor.getAvailability().equalsIgnoreCase("YES"))
            {
                updatedDoctors.add(doctor);
            }
        }
        
        return new ResponseEntity<List<Doctor>>(updatedDoctors, HttpStatus.OK);

    }

}
