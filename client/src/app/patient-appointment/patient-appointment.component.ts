import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Appointment } from '../../types/Appointment';
import { HttpErrorResponse } from '@angular/common/http';
import { MedicalRecord } from '../../types/MedicalRecord';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patient-appointment',
  templateUrl: './patient-appointment.component.html',
  styleUrls: ['./patient-appointment.component.scss']
})
export class PatientAppointmentComponent implements OnInit {
  appointmentList: Appointment[] = [];
  errorMessage: string | null = null;
  medicalRecord!: MedicalRecord;
  constructor(private router: Router, private httpService: HttpService) {
  
   }

  ngOnInit(): void 
  {
    this.getAppointments();
  }


  viewMedicalRecord(patientId:number | undefined, doctorId: number | undefined, medicalRecordId: number | undefined)
  {
    if(patientId !== undefined && doctorId !== undefined)
    {
      this.httpService.viewMedicalRecordByPatientIdDoctorId(patientId, doctorId).subscribe({
        next: (medicalRecord: MedicalRecord) => {
          this.medicalRecord = medicalRecord;
        },
        error: (error) => {
          this.handleError(error);
        }
      });
    }
    if(medicalRecordId !== undefined)
    {
      this.router.navigate([`/medicalRecords/view/${medicalRecordId}`]);
    }
    else
    {
      this.errorMessage = "Medical Record with ID: " + medicalRecordId + " not found."; 
    }
  }
  getAppointments() {
    const userIdString = localStorage.getItem('user_id');

    // Parse userId to an integer, if it exists
    const userId = userIdString ? parseInt(userIdString, 10) : null;
    this.appointmentList
    if(userId != null)
    {
      this.httpService.getAppointmentByPatient(userId).subscribe
      ({
        next: (data) =>{
          this.appointmentList = data;
          console.log(this.appointmentList);
        },
        error: (error: HttpErrorResponse) => {
          this.handleError(error);
        }
      })
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


