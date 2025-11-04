package com.wecp.healthcare_appointment_management_system.controller;

import com.wecp.healthcare_appointment_management_system.dto.LoginRequest;
import com.wecp.healthcare_appointment_management_system.dto.LoginResponse;
import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.entity.Receptionist;
import com.wecp.healthcare_appointment_management_system.entity.User;
import com.wecp.healthcare_appointment_management_system.jwt.JwtUtil;
import com.wecp.healthcare_appointment_management_system.repository.UserRepository;
import com.wecp.healthcare_appointment_management_system.service.UserService;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class RegisterAndLoginController 
{
    
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/patient/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient)
     {
        Patient newPatient = userService.registerPatient(patient);
        return new ResponseEntity<Patient>(newPatient ,HttpStatus.CREATED);
    }

    @PostMapping("/doctor/register")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor) 
    {
        Doctor newDoctor = userService.registerDoctor(doctor);
        return new ResponseEntity<Doctor>(newDoctor, HttpStatus.CREATED);
    }

    @PostMapping("/receptionist/register")
    public ResponseEntity<Receptionist> registerReceptionist(@RequestBody Receptionist receptionist) 
    {
       Receptionist newReceptionist = userService.registerReceptionist(receptionist);
       return new ResponseEntity<Receptionist>(newReceptionist, HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) 
    {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        }
        catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username (or) password", ex);
        }

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        
        return ResponseEntity.ok(new LoginResponse(jwt, user.getUsername(),user.getRole(), user.getId()));
        
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) 
    {
        
    String email = request.get("email");
    String oldPassword = request.get("oldPassword");
    String newPassword = request.get("newPassword");

    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isPresent()) {
        User user = userOpt.get();
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Old password is incorrect"));
        }
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
}

    
}
