package com.wecp.healthcare_appointment_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController 
{
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> fetchAppointments()
    {
        List<Appointment> appointments = appointmentService.getAllAppointments();

         if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Appointment>>(appointments,HttpStatus.OK);
    }

    @PostMapping
    public Appointment scheduleAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody TimeDto timeDto)
    {
        return this.appointmentService.scheduleAppointment(patientId, doctorId, timeDto);
    }

    @PutMapping
    public Appointment rescheduleAppointment(@RequestParam Long appointmentId, @RequestBody TimeDto timeDto)
    {
        return this.appointmentService.rescheduleAppointment(appointmentId, timeDto);
    }

    @GetMapping("/patient")
    public ResponseEntity<List<Appointment>> fetchAppointmentsByPatientId(@RequestParam Long patientId)
    {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);

         if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Appointment>>(appointments,HttpStatus.OK);
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<Appointment>> fetchAppointmentsByDoctorId(@RequestParam Long doctorId)
    {
        List<Appointment> appointments = this.appointmentService.getAppointmentsByDoctorId(doctorId);

         if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Appointment>>(appointments,HttpStatus.OK);
    }


}
