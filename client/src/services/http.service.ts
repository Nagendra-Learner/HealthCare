import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  readonly serverName: string = `${environment.apiUrl}`; 

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  registerPatient(details: any): Observable<any> {
    return this.http.post(
      `${this.serverName}/api/patient/register`,
      details,
      { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }
    );
  }

  registerDoctors(details: any): Observable<any> {
    return this.http.post(
      `${this.serverName}/api/doctors/register`,
      details,
      { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }
    );
  }

  registerReceptionist(details: any): Observable<any> {
    return this.http.post(
      `${this.serverName}/api/receptionist/register`,
      details,
      { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }
    );
  }

  getDoctors(): Observable<any> {
    return this.http.get(`${this.serverName}/api/patient/doctors`, {
      headers: this.getAuthHeaders(),
    });
  }
  getDoctorsForReceptionist(): Observable<any> {
    return this.http.get(`${this.serverName}/api/receptionist/doctors`, {
      headers: this.getAuthHeaders(),
    });
  }

  ScheduleAppointment(details: any): Observable<any> {
    const { patientId, doctorId,appointmentDate, time } = details;
    const combinedDateTime:string=`${appointmentDate} ${time}:00`;
    const timeDto={time: combinedDateTime};
    return this.http.post(
      `${this.serverName}/api/patient/appointment?patientId=${patientId}&doctorId=${doctorId}`,
      timeDto,
      { headers: this.getAuthHeaders() }
    );
  }

  ScheduleAppointmentByReceptionist(details: any): Observable<any> {
    const { patientId, doctorId,appointmentDate, time } = details;
    const combinedDateTime:string=`${appointmentDate} ${time}:00`;
    const timeDto={time: combinedDateTime};
    return this.http.post(
      `${this.serverName}/api/receptionist/appointment?patientId=${patientId}&doctorId=${doctorId}`,
      timeDto,
      { headers: this.getAuthHeaders() }
    );
  }

  reScheduleAppointment(appointmentId: number, timeDto: {time: string}): Observable<any> {
    return this.http.put(
      `${this.serverName}/api/receptionist/appointment-reschedule/${appointmentId}`,
      timeDto,
      { headers: this.getAuthHeaders() }
    );
  }

  getAllAppointments(): Observable<any> {
    return this.http.get(`${this.serverName}/api/receptionist/appointments`, {
      headers: this.getAuthHeaders(),
    });
  }

  getAppointmentByDoctor(doctorId: number): Observable<any> {
    return this.http.get(
      `${this.serverName}/api/doctor/appointments?doctorId=${doctorId}`,
      { headers: this.getAuthHeaders() }
    );
  }

  getAppointmentByPatient(patientId: number): Observable<any> {
    return this.http.get(
      `${this.serverName}/api/patient/appointments?patientId=${patientId}`,
      { headers: this.getAuthHeaders() }
    );
  }

  updateDoctorAvailability(doctorId: number, availability: string): Observable<any> {
    return this.http.post(
      `${this.serverName}/api/doctor/availability?doctorId=${doctorId}&availability=${availability}`,
      {},
      { headers: this.getAuthHeaders() }
    );
  }

  Login(loginDetails: any): Observable<any> {
    return this.http.post(`${this.serverName}/api/user/login`, loginDetails, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }
}