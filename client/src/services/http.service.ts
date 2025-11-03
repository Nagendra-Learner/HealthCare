import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../environments/environment.development';
import { Doctor } from '../types/Doctor';
import { MedicalRecord } from '../types/MedicalRecord';
import { Patient } from '../types/Patient';

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

  changePassword(data: { email: string | null, oldPassword: string, newPassword: string }) {
  return this.http.post<{ message: string }>(`${this.serverName}/api/user/change-password`, data);
}

  fetchAvailableDoctorsByPatient(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.serverName}/api/patient/available`);
  }

  fetchAvailableDoctorsByReceptionist(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.serverName}/api/receptionist/available`);
  }

  fetchAllPatients(): Observable<Patient[]>
  {
    return this.http.get<Patient[]>(`${this.serverName}/api/receptionist`);
  }

  scheduleAppointment(details: any): Observable<any> {
    const { patientId, doctorId,appointmentDate, time } = details;
    const combinedDateTime:string=`${appointmentDate} ${time}:00`;
    const timeDto={time: combinedDateTime};
    return this.http.post(
      `${this.serverName}/api/appointment?patientId=${patientId}&doctorId=${doctorId}`, timeDto);
  }


  reScheduleAppointment(appointmentId: number, timeDto: {time: string}): Observable<any> {
    return this.http.put(
      `${this.serverName}/api/appointment?appointmentId=${appointmentId}`, timeDto);
  }

  completedAppointment(appointmentId: number): Observable<any> {
    return this.http.put(
      `${this.serverName}/api/appointment/completed?appointmentId=${appointmentId}`, {});
  }

  fetchAllAppointments(): Observable<any> {
    return this.http.get(`${this.serverName}/api/appointment`);
  }

  fetchAppointmentsByDoctorId(doctorId: number): Observable<any> {
    return this.http.get(
      `${this.serverName}/api/appointment/doctor?doctorId=${doctorId}`);
  }

  fetchAppointmentsByPatientId(patientId: number): Observable<any> {
    return this.http.get(
      `${this.serverName}/api/appointment/patient?patientId=${patientId}`);
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
    return this.http.post<any>(`${this.serverName}/api/medicalRecord?patientId=${patientId}&doctorId=${doctorId}&appointmentId=${appointmentId}`, medicalrecord);
  }

  fetchMedicalRecordById(medicalRecordId: number)
  {
    return this.http.get<any>(`${this.serverName}/api/medicalRecord/${medicalRecordId}`);
  }

  isMedicalRecordExsists(patientId: number, doctorId: number)
  {
    return this.http.get<MedicalRecord>(`${this.serverName}/api/medicalRecord/patientAndDoctor?patientId=${patientId}&doctorId=${doctorId}`);
  }

  updateMedicalRecord(medicalRecordId: number, medicalRecord: MedicalRecord)
  {
    return this.http.put<MedicalRecord>(`${this.serverName}/api/medicalRecord?medicalRecordId=${medicalRecordId}`, medicalRecord);
  }
}