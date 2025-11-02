package com.wecp.healthcare_appointment_management_system.repository;

import com.wecp.healthcare_appointment_management_system.entity.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
 
@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, Long>  
{
    public Optional<Receptionist> findByUsername(String username);
    public Optional<Receptionist> findByEmail(String email);
}
