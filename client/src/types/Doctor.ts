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
    
    // constructor(id: number, username: string, password: string, email: string, role: string,  specialty: string, availability : string, appointments: Appointment, medicalRecords: MedicalRecord)
    // {
    //     this.id = id;
    //     this.username = username;
    //     this.password = password;
    //     this.email = email;
    //     this.role = role;
    //     this.specialty = specialty;
    //     this.availability = availability;
    //     this.appointments = appointments;
    //     this.medicalRecords = medicalRecords;
    // }

}