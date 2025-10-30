import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../../services/http.service';
import { Doctor } from '../../types/Doctor';

@Component({
  selector: 'app-schedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.scss']
})
export class ScheduleAppointmentComponent implements OnInit {
  itemForm!: FormGroup;
  doctors: Doctor[] = [];
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

  ngOnInit(): void {
    this.loadDoctors();
    this.patient = Number(localStorage.getItem('user_id'));

    const now = new Date();
    this.today = now.toISOString().split('T')[0]; // yyyy-MM-dd
    this.currentTime = now.toTimeString().slice(0, 5); // HH:mm

    this.itemForm = this.formBuilder.group({
      doctorId: [null, Validators.required],
      patientId: [this.patient],
      appointmentDate: ['', Validators.required],
      time: ['', Validators.required],
      reason: ['', Validators.required]
    });
  }

  loadDoctors(): void {
    this.httpService.getDoctors().subscribe({
      next: (data: Doctor[]) => {
        this.doctors = data;
        this.availableDoctors = this.doctors.filter(
          d => d.availability?.toLowerCase() === 'yes'
        );
      },
      error: (error: HttpErrorResponse) => this.handleError(error)
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.itemForm.valid) {
      const { appointmentDate, time } = this.itemForm.value;
      const now = new Date();
      const selectedDateTime = new Date(`${appointmentDate}T${time}`);

      if (selectedDateTime < now) {
        this.errorMessage = 'You cannot select a past date or time!';
        return;
      }

      this.httpService.ScheduleAppointment(this.itemForm.value).subscribe({
        next: (response: any) => {
          this.successMessage = 'Appointment scheduled successfully!';
          this.errorMessage = null;
          setTimeout(() => {
            this.itemForm.reset();
            this.router.navigate(['/patient-appointment']);
          }, 2000);
        },
        error: (error: any) => this.handleError(error)
      });
    } else {
      this.errorMessage = 'Please fill all required fields!';
    }
  }

  get f() {
    return this.itemForm.controls;
  }

  private handleError(error: HttpErrorResponse): void {
    if (error.error instanceof ErrorEvent) {
      this.errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      if (error.status === 400) {
        this.errorMessage = 'Bad request. Please check your input.';
      }
      else if(error.status === 409)
      {
        this.errorMessage = `Server-side error: ${error.error}`;
      }
    }
    this.successMessage = null;
  }
}