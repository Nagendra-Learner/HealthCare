package com.wecp.healthcare_appointment_management_system.controller;
import com.wecp.healthcare_appointment_management_system.dto.LoginRequest;
import com.wecp.healthcare_appointment_management_system.entity.Appointment;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.User;
import com.wecp.healthcare_appointment_management_system.service.AppointmentService;
import com.wecp.healthcare_appointment_management_system.service.DoctorService;
import com.wecp.healthcare_appointment_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/api/doctor/appointments")
    public ResponseEntity<List<Appointment>> viewAppointments(@RequestParam Long doctorId) {
        List<Appointment> appointments = doctorService.getAppointments(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/api/doctor/availability")
    public ResponseEntity<Doctor> manageAvailability(@RequestParam Long doctorId, @RequestParam String availability) {
        Doctor d = doctorService.manageAvailability(doctorId, availability);
        if(d != null)
        {
            return ResponseEntity.ok(d);
        }
        return new ResponseEntity<>(d, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/doctor/register")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor)
    {
        Doctor d = doctorService.registerDoctor(doctor);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @GetMapping("/api/doctor")
    public ResponseEntity<List<Doctor>> getDoctors()
    {
        return new ResponseEntity<>(doctorService.getDoctors(), HttpStatus.OK);
    }
}
