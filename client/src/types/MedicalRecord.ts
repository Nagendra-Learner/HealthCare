import { Doctor } from "./Doctor";
import { Patient } from "./Patient";

export class MedicalRecord
{
    id?: number;
    patient: Patient;
    doctor: Doctor;
    diagnosis: string;
    prescription: string;
    notes: string;
    recordDate: Date;

    constructor(id: number, patient: Patient, doctor: Doctor, diagnosis: string, prescription: string, notes: string, recordDate: Date)
    {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
        this.recordDate = recordDate;
    }

}