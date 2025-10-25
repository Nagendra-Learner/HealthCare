package com.wecp.healthcare_appointment_management_system.repository;

import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> 
{
    
    @Query("Select m from MedicalRecord m where m.patient.id = :patientId")
    List<MedicalRecord> getMedicalRecordsByPatientId(@Param("patientId") Long patientId);

    @Query("Select m from MedicalRecord m where m.doctor.id = :doctorId")
    List<MedicalRecord> getMedicalRecordsByDoctorId(@Param("doctorId") Long doctorId);

}
