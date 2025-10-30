package com.wecp.healthcare_appointment_management_system.controller;

import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.exceptions.DuplicateEntityException;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
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
@RequestMapping("/api/receptionist")
public class ReceptionistController 
{

  @Autowired
  private AppointmentService appointmentService;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException enfEx)
    {
        return new ResponseEntity<>(enfEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<String> handleDuplicateEntityException(DuplicateEntityException deEx)
    {
        return new ResponseEntity<>(deEx.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException sqlEx)
    {
        return new ResponseEntity<>(sqlEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

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
       
        if(timeDto==null || timeDto.getTime()==null)
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
              
        LocalDateTime newTime = timeDto.getTime();
        Appointment updatedAppointment = appointmentService.rescheduleAppointment(appointmentId, newTime);
            
        return new ResponseEntity<Appointment>(updatedAppointment,HttpStatus.OK);
        
    }

}
