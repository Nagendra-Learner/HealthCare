package com.wecp.healthcare_appointment_management_system.controller;

import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.service.AppointmentService;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/receptionist")
public class ReceptionistController 
{
    @Autowired
    DoctorService doctorService;

  @Autowired
  private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAppointments() 
    {
        List<Appointment> appointments = appointmentService.getAppointments();

        if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
    }

    @PostMapping("/appointment")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody TimeDto timeDto) 
    {
       
        if(timeDto == null || timeDto.getTime() == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = appointmentService.scheduleAppointments(patientId,doctorId,timeDto);
        return new ResponseEntity<Appointment>(appointment,HttpStatus.CREATED);

      }
        

    @PutMapping("/appointment-reschedule/{appointmentId}")
    public ResponseEntity<Appointment> rescheduleAppointment(@PathVariable Long appointmentId, @RequestBody TimeDto timeDto) 
    {
          if (timeDto == null || timeDto.getTime() == null) 
          {
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
              
        Appointment updatedAppointment = appointmentService.rescheduleAppointment(appointmentId, timeDto);
            
        return new ResponseEntity<Appointment>(updatedAppointment,HttpStatus.OK);
        
    }
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() 
    {
        List<Doctor> doctors = doctorService.getDoctors();
        if(doctors.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Doctor>>(doctorService.getDoctors(),HttpStatus.OK);
    }

}
