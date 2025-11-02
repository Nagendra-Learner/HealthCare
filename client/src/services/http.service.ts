import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../environments/environment.development';
import { Doctor } from '../types/Doctor';
import { MedicalRecord } from '../types/MedicalRecord';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  readonly serverName: string = `${environment.apiUrl}`; 

  constructor(private http: HttpClient, private authService: AuthService) {}

  registerPatient(details: any): Observable<any> {
    return this.http.post(
      `${this.serverName}/api/patient/register`,
      details);
  }

  registerDoctor(details: any): Observable<any> {
    return this.http.post(
      `${this.serverName}/api/doctor/register`,
      details);
  }

  registerReceptionist(details: any): Observable<any> {
    return this.http.post(
      `${this.serverName}/api/receptionist/register`,
      details);
  }

  getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.serverName}/api/patient/doctors`);
  }

  getDoctorsForReceptionist(): Observable<any> {
    return this.http.get(`${this.serverName}/api/receptionist/doctors`);
  }

  ScheduleAppointment(details: any): Observable<any> {
    const { patientId, doctorId,appointmentDate, time } = details;
    const combinedDateTime:string=`${appointmentDate} ${time}:00`;
    const timeDto={time: combinedDateTime};
    return this.http.post(
      `${this.serverName}/api/patient/appointment?patientId=${patientId}&doctorId=${doctorId}`,
      timeDto);
  }

  ScheduleAppointmentByReceptionist(details: any): Observable<any> {
    const { patientId, doctorId,appointmentDate, time } = details;
    const combinedDateTime:string=`${appointmentDate} ${time}:00`;
    const timeDto={time: combinedDateTime};
    return this.http.post(
      `${this.serverName}/api/receptionist/appointment?patientId=${patientId}&doctorId=${doctorId}`,
      timeDto);
  }

  reScheduleAppointment(appointmentId: number, timeDto: {time: string}): Observable<any> {
    return this.http.put(
      `${this.serverName}/api/receptionist/appointment-reschedule/${appointmentId}`,
      timeDto);
  }

  getAllAppointments(): Observable<any> {
    return this.http.get(`${this.serverName}/api/receptionist/appointments`);
  }

  getAppointmentByDoctor(doctorId: number): Observable<any> {
    return this.http.get(
      `${this.serverName}/api/doctor/appointments?doctorId=${doctorId}`);
  }

  getAppointmentByPatient(patientId: number): Observable<any> {
    return this.http.get(
      `${this.serverName}/api/patient/appointments?patientId=${patientId}`);
  }

  updateDoctorAvailability(doctorId: number, availability: string): Observable<any> {
    return this.http.put(
      `${this.serverName}/api/doctor/availability?doctorId=${doctorId}&availability=${availability}`, {});
  }

  login(loginDetails: any): Observable<any> {
    return this.http.post<any>(`${this.serverName}/api/user/login`, loginDetails);
  }

  createMedicalRecord(patientId: number, doctorId: number, appointmentId: number, medicalrecord: any)
  {
    return this.http.post<any>(`${this.serverName}/api/doctor/medicalrecords/${patientId}/${doctorId}/${appointmentId}`, medicalrecord);
  }

  viewMedicalRecordById(medicalRecordId: number)
  {
    return this.http.get<any>(`${this.serverName}/api/doctor/medicalrecords?medicalRecordId=${medicalRecordId}`);
  }

  viewMedicalRecordByPatientIdDoctorId(patientId: number, doctorId: number)
  {
    return this.http.get<MedicalRecord>(`${this.serverName}/api/doctor/medicalrecords/${patientId}/${doctorId}`);
  }

  updateMedicalRecord(medicalRecordId: number, medicalRecord: MedicalRecord)
  {
    return this.http.put<MedicalRecord>(`${this.serverName}/api/doctor/medicalrecords/${medicalRecordId}`, medicalRecord);
  }
}