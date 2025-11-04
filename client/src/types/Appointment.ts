import { Patient } from "./Patient";
import { Doctor } from "./Doctor";
import { MedicalRecord } from "./MedicalRecord";

export interface Appointment
{
    id: number;
    patient: Patient;
    doctor: Doctor;
    appointmentTime: Date;
    status: string;
    medicalRecord: MedicalRecord;
}