
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../../services/http.service';

@Component({
  selector: 'app-schedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.scss']
})
export class ScheduleAppointmentComponent implements OnInit {
  itemForm!: FormGroup;
  doctors:any;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private httpService:HttpService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadDoctors();
    this.itemForm = this.formBuilder.group({
      doctorId: [null, Validators.required],
      patientId: [null, Validators.required],
      //appointmentDate: ['', Validators.required],
      time: ['', Validators.required],
      //reason: ['', Validators.required]
    });
  }

  loadDoctors(): void {
    this.httpService.getDoctors().subscribe({
      next: (data:any[]) => {
        this.doctors = data;
      },
      error: (error: HttpErrorResponse) => {
        this.handleError(error);
      }
    });
  }

  onSubmit(): void {
    if (this.itemForm.valid) {
      this.httpService.ScheduleAppointment(this.itemForm.value).subscribe({
        next: (response:any) => {
          this.errorMessage = null;
          console.log('Appointment scheduled:', response);
          setTimeout(() => {
            this.itemForm.reset();
            this.router.navigate(['/appointments']);
          }, 2000);
        },
        error: (error:any) => {
          this.handleError(error);
        },
        complete: () => {
          this.successMessage = 'Appointment scheduled successfully!';
        }
      });
    }
  }

  private handleError(error: HttpErrorResponse): void {
    if (error.error instanceof ErrorEvent) {
      this.errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      this.errorMessage = `Server-side error: ${error.status} ${error.message}`;
      if (error.status === 400) {
        this.errorMessage = 'Bad request. Please check your input.';
      }
    }
    this.successMessage = null;
  }
}
