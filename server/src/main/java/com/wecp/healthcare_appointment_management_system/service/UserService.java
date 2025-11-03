package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.entity.Receptionist;
import com.wecp.healthcare_appointment_management_system.entity.User;
import com.wecp.healthcare_appointment_management_system.exceptions.DuplicateEntityException;
import com.wecp.healthcare_appointment_management_system.exceptions.EntityNotFoundException;
import com.wecp.healthcare_appointment_management_system.repository.DoctorRepository;
import com.wecp.healthcare_appointment_management_system.repository.PatientRepository;
import com.wecp.healthcare_appointment_management_system.repository.ReceptionistRepository;
import com.wecp.healthcare_appointment_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private DoctorRepository doctorRepository;

   @Autowired
   private PatientRepository patientRepository;

   @Autowired
   private ReceptionistRepository receptionistRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Doctor registerDoctor(Doctor doctor)
    {
        User user1 = this.userRepository.findByUsername(doctor.getUsername()).orElse(null);

        if(user1 != null)
        {
            if(user1.getUsername().equals(doctor.getUsername()))
            {
                throw new DuplicateEntityException("Username already exists.");
            }
        }

        User user2  = this.userRepository.findByEmail(doctor.getEmail()).orElse(null);

        if(user2 != null)
        {
            if(user2.getEmail().equals(doctor.getEmail()))
            {
                throw new DuplicateEntityException("Email already exists.");
            }
        }

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        
        return doctorRepository.save(doctor);
        
    }

    public Patient registerPatient(Patient patient)
    {
        User user1 = this.userRepository.findByUsername(patient.getUsername()).orElse(null);

        if(user1 != null)
        {
            if(user1.getUsername().equals(patient.getUsername()))
            {
                throw new DuplicateEntityException("Username already exists.");
            }
        }

        User user2 = this.userRepository.findByEmail(patient.getEmail()).orElse(null);

        if(user2 != null)
        {
            if(user2.getEmail().equals(patient.getEmail()))
            {
                throw new DuplicateEntityException("Email already exists.");
            }
        }

        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        return patientRepository.save(patient);
    }

    public Receptionist registerReceptionist(Receptionist receptionist)
    {
        User user1 = this.userRepository.findByUsername(receptionist.getUsername()).orElse(null);

        if(user1 != null)
        {
            if(user1.getUsername().equals(receptionist.getUsername()))
            {
                throw new DuplicateEntityException("Username already exists.");
            } 
        }

        User user2 = this.userRepository.findByEmail(receptionist.getEmail()).orElse(null);

        if(user2 != null)
        {
            if(user2.getEmail().equals(receptionist.getEmail()))
            {
                throw new DuplicateEntityException("Email already exists.");
            }
        }

        receptionist.setPassword(passwordEncoder.encode(receptionist.getPassword()));

        return receptionistRepository.save(receptionist);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        Optional<User> user = this.userRepository.findByUsername(username);
        if(user.isPresent())
        {
            return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),    
                user.get().getPassword(), 
                Collections.singletonList(new SimpleGrantedAuthority(user.get().getRole())));
        }
        
        throw new EntityNotFoundException("User " + username + " not found.");
    }

}
