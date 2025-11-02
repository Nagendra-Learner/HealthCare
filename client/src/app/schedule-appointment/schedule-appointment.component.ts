import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../../services/http.service';
import { Doctor } from '../../types/Doctor';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-schedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.scss']
})
export class ScheduleAppointmentComponent implements OnInit {
  itemForm!: FormGroup;
  availableDoctors: Doctor[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;
  patient!: number;
  today: string = '';
  currentTime: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private httpService: HttpService,
    private router: Router
  ) {}

  ngOnInit(): void 
  {
    this.patient = Number(localStorage.getItem('user_id'));
    const now = new Date();
    this.today = now.toISOString().split('T')[0]; // yyyy-MM-dd
    this.currentTime = now.toTimeString().slice(0, 5); // HH:mm

    this.itemForm = this.formBuilder.group({
      doctorId: [null, Validators.required],
      patientId: [this.patient],
      appointmentDate: ['', Validators.required],
      time: ['', Validators.required],
      reason: ['', [Validators.minLength(7)]],
    }, {validators: this.futureDateTimeValidator});

    this.loadDoctors();
  }

  loadDoctors(): void {
    this.httpService.getDoctors().pipe(
      map((doctors: Doctor[]) => doctors.filter(d => d.availability?.toLowerCase() === 'yes')),
      catchError((error: HttpErrorResponse) => {
        this.handleError(error);
        return of([]);
      })
    ).subscribe({
      next: (data: Doctor[]) => {
        this.availableDoctors = data;
      }
    });
    
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.itemForm.valid)
      {
      this.httpService.ScheduleAppointment(this.itemForm.value).subscribe({
        next: (response: any) => {
          this.successMessage = 'Appointment scheduled successfully!';
          this.errorMessage = null;
          setTimeout(() => {
            this.successMessage = null;
            this.itemForm.reset();
            this.router.navigate(['/patient-appointment']);
          }, 2000);
        },
        error: (error: HttpErrorResponse) => this.handleError(error)
      });
    } else {
      this.errorMessage = 'Please fill all required fields!';
    }
  }

  get f() {
    return this.itemForm.controls;
  }

  private futureDateTimeValidator(group: AbstractControl) : { [key: string]: any} | null
  {
    const date = group.get('appointmentDate')?.value;
    const time = group.get('time')?.value;

    if(date && time)
    {
      const selectedDateTime = new Date(`${date}T${time}`);
      if(selectedDateTime < new Date())
      {
        return {pastDateTime: true};
      }
    }
    return (null);
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