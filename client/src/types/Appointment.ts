import { Patient } from "./Patient";
import { Doctor } from "./Doctor";

export class Appointment
{
    id: number;
    patient: Patient;
    doctor: Doctor;
    appointmentTime: Date;
    status: string;

    constructor( id: number, patient: Patient, doctor: Doctor, appointmentTime: Date, status: string)
    {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

}