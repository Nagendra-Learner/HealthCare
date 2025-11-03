import { Appointment } from "./Appointment";
import { MedicalRecord } from "./MedicalRecord";

export interface Doctor
{
    id?: number;
    username: string;
    password: string;
    email: string;
    role: string;
    specialty: string;
    availability : string;
    appointments: Appointment;
    medicalRecords: MedicalRecord;

}