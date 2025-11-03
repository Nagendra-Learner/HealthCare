package com.wecp.healthcare_appointment_management_system.controller;

import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController 
{
    @Autowired
    private DoctorService doctorService;


    @GetMapping("/appointment")
    public ResponseEntity<List<Appointment>> fetchAppointments(@RequestParam Long doctorId) 
    {

        List<Appointment> appointments = doctorService.getAppointments(doctorId);

        if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(appointments);

    }

    @PutMapping("/availability")
    public ResponseEntity<Doctor> manageAvailability(@RequestParam Long doctorId, @RequestParam String availability) 
    {
        Doctor doctor = doctorService.manageAvailability(doctorId, availability);
    
        return ResponseEntity.ok(doctor);

    }

}
