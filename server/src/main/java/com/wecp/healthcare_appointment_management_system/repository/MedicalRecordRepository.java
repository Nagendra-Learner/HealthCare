package com.wecp.healthcare_appointment_management_system.repository;

import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> 
{   
    @Query("Select m from MedicalRecord m where m.patient.id = :patientId And m.doctor.id = :doctorId")
    MedicalRecord findMedicalRecordByPatientIdDoctorId(Long patientId, Long doctorId);

}
