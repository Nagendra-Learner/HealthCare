import { Appointment } from "./Appointment";
import { Doctor } from "./Doctor";
import { Patient } from "./Patient";

export interface MedicalRecord
{
    id?: number;
    patient: Patient;
    doctor: Doctor;
    diagnosis: string;
    prescription: string;
    notes: string;
    recordDate: Date;
    appointment: Appointment;

}