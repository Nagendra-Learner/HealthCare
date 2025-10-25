package com.wecp.healthcare_appointment_management_system.controller;
import com.wecp.healthcare_appointment_management_system.dto.TimeDto;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.service.AppointmentService;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;
import com.wecp.healthcare_appointment_management_system.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class PatientController 
{
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    DoctorService doctorService;

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

    @GetMapping("/api/patient/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() 
    {
        List<Doctor> doctors = doctorService.getDoctors();
        if(doctors.isEmpty())
        {
            throw new RuntimeException("No doctors available.");
        }
        return new ResponseEntity<List<Doctor>>(doctorService.getDoctors(),HttpStatus.OK);
    }

    @PostMapping("/api/patient/appointment")
    public ResponseEntity<?> scheduleAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody TimeDto timeDto) 
    {
        return new ResponseEntity<>(appointmentService.scheduleAppointments(patientId,doctorId,timeDto),HttpStatus.OK);
    }

    @GetMapping("/api/patient/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(@RequestParam Long patientId) 
    {
        List<Appointment> appointments = this.appointmentService.getAppointmentsByPatientId(patientId);
        if(appointments.isEmpty())
        {
            throw new RuntimeException("No appoints for patient with ID: " + patientId);
        }
        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }

    @GetMapping("/api/patient/medicalrecords")
    public ResponseEntity<List<MedicalRecord>> viewMedicalRecords(@RequestParam Long patientId) {
        // view medical records
        return new ResponseEntity<>(medicalRecordService.viewMedicalRecordsByPatientId(patientId),HttpStatus.OK);
    }
}
