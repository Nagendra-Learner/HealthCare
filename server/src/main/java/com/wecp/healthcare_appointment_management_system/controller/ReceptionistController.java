package com.wecp.healthcare_appointment_management_system.controller;


import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReceptionistController {

  @Autowired
  private AppointmentService appointmentService;

    @GetMapping("/api/receptionist/appointments")
    public List<Appointment> getAppointments() {
        List<Appointment> list = appointmentService.getAppointments();
        if(list.isEmpty())
          return new ArrayList<>();

        return list;
    }

    @PostMapping("/api/receptionist/appointment")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestParam Long patientId,
                                                           @RequestParam Long doctorId,
                                                           @RequestBody TimeDto timeDto) {
        // schedule appointment
        try
        {
        if(timeDto==null || timeDto.getTime()==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Appointment appointment = appointmentService.scheduleAppointments(patientId,doctorId,timeDto);
        if (appointment == null) 
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
        return new ResponseEntity<>(appointment,HttpStatus.OK);
        }
        catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
      }


    @PutMapping("/api/receptionist/appointment-reschedule/{appointmentId}")
    public ResponseEntity<Appointment> rescheduleAppointment(@PathVariable Long appointmentId,
                                                             @RequestBody TimeDto timeDto) {
        try {
          if (timeDto == null || timeDto.getTime() == null) 
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
              
         LocalDateTime newTime = timeDto.getTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
         Date date = Date.from(newTime.atZone(ZoneId.systemDefault()).toInstant());
         Appointment updatedAppointment = appointmentService.rescheduleAppointment(appointmentId, date);
         
         if (updatedAppointment == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
        return new ResponseEntity<>(updatedAppointment,HttpStatus.OK);
        }
        catch(Exception e){
          e.printStackTrace();
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
