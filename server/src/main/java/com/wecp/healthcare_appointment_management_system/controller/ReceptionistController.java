package com.wecp.healthcare_appointment_management_system.controller;

import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
// import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class ReceptionistController 
{

  @Autowired
  private AppointmentService appointmentService;

   @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException rtEx)
    {
        return new ResponseEntity<>(rtEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException sqlEx)
    {
        return new ResponseEntity<>(sqlEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/api/receptionist/appointments")
    public List<Appointment> getAppointments() 
    {
        List<Appointment> appointments = appointmentService.getAppointments();

        if(appointments.isEmpty())
        {
          throw new RuntimeException("No appointments found.");
        }

        return appointments;
    }

    @PostMapping("/api/receptionist/appointment")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody TimeDto timeDto) 
    {
       
        if(timeDto==null || timeDto.getTime()==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = appointmentService.scheduleAppointments(patientId,doctorId,timeDto);
        return new ResponseEntity<>(appointment,HttpStatus.OK);

      }
        

    @PutMapping("/api/receptionist/appointment-reschedule/{appointmentId}")
    public ResponseEntity<Appointment> rescheduleAppointment(@PathVariable Long appointmentId, @RequestBody TimeDto timeDto) 
    {
          if (timeDto == null || timeDto.getTime() == null) 
          {
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
              
        LocalDateTime newTime = timeDto.getTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        Date date = Date.from(newTime.atZone(ZoneId.systemDefault()).toInstant());
        Appointment updatedAppointment = appointmentService.rescheduleAppointment(appointmentId, date);
            
        return new ResponseEntity<>(updatedAppointment,HttpStatus.OK);
        
    }

}
