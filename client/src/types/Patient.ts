import { Appointment } from "./Appointment";
import { MedicalRecord } from "./MedicalRecord";

export interface Patient
{
    id?: number;
    username: string;
    password: string;
    email: string;
    role: string;
    appointments: Appointment;
    medicalRecords: MedicalRecord;  
}