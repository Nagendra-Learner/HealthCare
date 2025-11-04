import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpService } from '../../services/http.service';
import { AuthService } from '../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-doctor-availability',
  templateUrl: './doctor-availability.component.html',
  styleUrls: ['./doctor-availability.component.scss']
})
export class DoctorAvailabilityComponent implements OnInit
{
  availabilityForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null  = null;

  constructor( private fb: FormBuilder, private httpService: HttpService, private authService: AuthService, private router: Router) 
  {}

  ngOnInit()
  {
    this.availabilityForm = this.fb.group({ availability: ['', Validators.required]});
  }

  onSubmit(): void 
  {
    this.successMessage = null;
    this.errorMessage = null;

    if(this.availabilityForm.valid)
    {
      const userIdString = localStorage.getItem('user_id');
      const doctorId = userIdString ? parseInt(userIdString, 10) : null;
      const availability = this.availabilityForm.value.availability;

      if(doctorId !== null && !isNaN(doctorId))
      {
        console.log(doctorId);
        this.httpService.updateDoctorAvailability(doctorId, availability).subscribe({
            next: () => {
              this.successMessage = 'Availability updated successfully.';
              setTimeout(() => {
                this.successMessage = null;
                this.availabilityForm.reset();
                this.router.navigate(['/dashboard']);
              }, 2000);
            },
            error: (error: HttpErrorResponse) => {
              this.handleError(error);
            }
          });
      }
      else
      {
        this.errorMessage = 'Invalid Doctor ID!';
      }
    }
    else
    {
      this.errorMessage = "Please fill availability.";
    }
  }

  get f()
  {
    return this.availabilityForm.controls;
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

