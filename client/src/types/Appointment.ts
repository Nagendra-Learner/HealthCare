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

    // constructor( id: number, patient: Patient, doctor: Doctor, appointmentTime: Date, status: string)
    // {
    //     this.id = id;
    //     this.patient = patient;
    //     this.doctor = doctor;
    //     this.appointmentTime = appointmentTime;
    //     this.status = status;
    // }

}