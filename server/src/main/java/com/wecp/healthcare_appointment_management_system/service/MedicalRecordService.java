package com.wecp.healthcare_appointment_management_system.service;

import com.wecp.healthcare_appointment_management_system.entity.MedicalRecord;
import com.wecp.healthcare_appointment_management_system.entity.Patient;
import com.wecp.healthcare_appointment_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordService {
@Autowired
MedicalRecordRepository medicalRecordRepository;

public List<MedicalRecord> viewMedicalRecordsByDoctorId(Long doctorId)
{
    return medicalRecordRepository.getMedicalRecordsByDoctorId(doctorId);
}
public List<MedicalRecord> viewMedicalRecordsByPatient(Long patientId)
{
    return medicalRecordRepository.getMedicalRecordsByPatientId(patientId);
}
public long createMedicalReport(MedicalRecord medicalRecord)
{
    medicalRecordRepository.save(medicalRecord);
    return medicalRecord.getPatientId();
}

public void updateMedicalReport(MedicalRecord medicalRecord)
{
    MedicalRecord m=medicalRecordRepository.findById(medicalRecord.getPatientId()).get();
    if(medicalRecordRepository.findById(medicalRecord.getPatientId()).isPresent())
    {
        m.setDiagnosis(medicalRecord.getDiagnosis());
        m.setNotes(medicalRecord.getNotes());
        m.setPrescription(medicalRecord.getPrescription());
        m.setRecordDate(medicalRecord.getRecordDate());
       // m.setPatientId(medicalRecord.getPatientId());
       medicalRecordRepository.save(m);
    }
}

}
