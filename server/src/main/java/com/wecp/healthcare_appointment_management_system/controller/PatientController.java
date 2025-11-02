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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController 
{
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    DoctorService doctorService;


    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() 
    {
        List<Doctor> doctors = doctorService.getDoctors();

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

    @PostMapping("/appointment")
    public ResponseEntity<?> scheduleAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody TimeDto timeDto) 
    {
        if (timeDto == null || timeDto.getTime() == null) 
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(appointmentService.scheduleAppointments(patientId, doctorId, timeDto),HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(@RequestParam Long patientId) 
    {
        List<Appointment> appointments = this.appointmentService.getAppointmentsByPatientId(patientId);
        if(appointments.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }

    @GetMapping("/medicalrecords")
    public ResponseEntity<List<MedicalRecord>> viewMedicalRecords(@RequestParam Long patientId) 
    {
        List<MedicalRecord> medicalRecords = medicalRecordService.viewMedicalRecordsByPatientId(patientId);

        if(medicalRecords.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(medicalRecords,HttpStatus.OK);
    }

}
