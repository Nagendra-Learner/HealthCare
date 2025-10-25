package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.entity.Doctor;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.entity.Receptionist;
import com.wecp.healthcare_appointment_management_system.repository.DoctorRepository;
import com.wecp.healthcare_appointment_management_system.repository.PatientRepository;
import com.wecp.healthcare_appointment_management_system.repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ReceptionistRepository receptionistRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Doctor registerDoctor(Doctor doctor)
    {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
        
    }

    public Patient registerPatient(Patient patient)
    {
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }

    public Receptionist registerReceptionist(Receptionist receptionist)
    {
        receptionist.setPassword(passwordEncoder.encode(receptionist.getPassword()));
        return receptionistRepository.save(receptionist);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Doctor> doctor= doctorRepository.findByUsername(username);
        if(doctor.isPresent())
        {
            return new org.springframework.security.core.userdetails.User(
                doctor.get().getUsername(),    
                doctor.get().getPassword(), 
                Collections.singletonList(new SimpleGrantedAuthority(doctor.get().getRole())));
        }

        Optional<Patient> patient= patientRepository.findByUsername(username);
        if(patient.isPresent())
        {
            return new org.springframework.security.core.userdetails.User(
                patient.get().getUsername(), 
                patient.get().getPassword(), 
                Collections.singletonList(new SimpleGrantedAuthority(patient.get().getRole())));
        }

        Optional<Receptionist> receptionist= receptionistRepository.findByUsername(username);
        if(receptionist.isPresent())
        {
            return new org.springframework.security.core.userdetails.User(
                receptionist.get().getUsername(),
                receptionist.get().getPassword(), 
                Collections.singletonList(new SimpleGrantedAuthority(receptionist.get().getRole())));
        }
        
        throw new UsernameNotFoundException("User " + username + " not found.");
    }

    



}
