import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { Appointment } from '../../types/Appointment';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { MedicalRecord } from '../../types/MedicalRecord';
import { Patient } from '../../types/Patient';

@Component({
  selector: 'app-doctor-appointment',
  templateUrl: './doctor-appointment.component.html',
  styleUrls: ['./doctor-appointment.component.scss']
})
export class DoctorAppointmentComponent implements OnInit 
{
  appointments: Appointment[] = [];
  errorMessage: string | null = null;
  doctorId!: number | null;
  patientId!: number | undefined;
  medicalRecord!: MedicalRecord;

  constructor(private httpService: HttpService, private router: Router) {}

  ngOnInit(): void 
  {
    this.getAppointments();
  }

  getAppointments(): void 
  {
    this.errorMessage = null;
    const userIdString = localStorage.getItem('user_id');
    this.doctorId = userIdString ? parseInt(userIdString, 10) : null;
    
    if(this.doctorId!==null && !isNaN(this.doctorId))
    {
      this.httpService.getAppointmentByDoctor(this.doctorId).subscribe({
        next: (data: Appointment[]) => {
          this.appointments = data;
        },
        error: (error: HttpErrorResponse) => {
          console.error(error.error);
          this.handleError(error);  
        }
      });
    }
    else
    {
      this.errorMessage = 'Invalid Doctor ID!'; 
    }
  }

  createMedicalRecord(appointmentId: number, patientId: number | undefined): void
  {
    if(patientId)
    {
      this.router.navigate([`/medicalRecords/create/${patientId}/${this.doctorId}/${appointmentId}`]);
    }
    else
    {
      this.errorMessage = "Patient with ID: " + patientId + " not found.";
    }
  }

  // medicalRecordIsExists(patientId: number, doctorId: number)
  // {
  //   this.httpService.viewMedicalRecordByPatientIdDoctorId(patientId, doctorId).subscribe({
  //     next: (data) => {
  //       this.medicalRecord = data;
  //     },
  //     error: (error) => {
  //       this.handleError(error);
  //     }
  //   })
  // }

  viewMedicalRecord(medicalRecordId: number | undefined)
  {
    if(medicalRecordId !== undefined)
    {
      this.router.navigate([`/medicalRecords/view/${medicalRecordId}`]);
    }
    else
    {
      this.errorMessage = "Medical Record with ID: " + medicalRecordId + " not found."; 
    }
  }

  editMedicalRecord(patientId: number | undefined, medicalRecordId: number | undefined, appointmentId: number | undefined)
  {
    if(medicalRecordId !== undefined && patientId !== undefined && appointmentId !== undefined)
      {
        this.router.navigate([`/medicalRecords/edit/${medicalRecordId}/${patientId}/${this.doctorId}/${appointmentId}`]);
      }
      else
      {
        this.errorMessage = "Medical Record with ID: " + medicalRecordId + " not found."; 
      } 
  }

  private handleError(error: HttpErrorResponse): void 
  {
    console.log(typeof error)
    console.log(error)
    if (error.error instanceof ErrorEvent) 
    {
      this.errorMessage = `Client-side error: ${error.error.message}`;
    }
    else 
    {
      if(typeof error.error === "string")
      {
        console.log(error.status);
        this.errorMessage = error.error;
      }
      else if(error.error?.message)
      {
        this.errorMessage = error.error.message;
      }
    }
  }
}