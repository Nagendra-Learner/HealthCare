import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpService } from '../../services/http.service';
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-doctor-availability',
  templateUrl: './doctor-availability.component.html',
  styleUrls: ['./doctor-availability.component.scss']
})
export class DoctorAvailabilityComponent implements OnInit
{
  availabilityForm!: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor( private fb: FormBuilder, private httpService: HttpService, private authService: AuthService) 
  {
  }

  ngOnInit()
  {
    this.availabilityForm = this.fb.group({ availability: ['', Validators.required]});
  }

  onSubmit(): void 
  {
    this.successMessage = '';
    this.errorMessage = '';

    if(this.availabilityForm.valid)
    {
      const userIdString = localStorage.getItem('user_id');
      const doctorId = userIdString ? parseInt(userIdString, 10) : null;
      const availability = this.availabilityForm.value.availability;

      if(doctorId!==null && !isNaN(doctorId))
      {
        console.log(doctorId);
        this.httpService.updateDoctorAvailability(doctorId, availability).subscribe({
            next: () => {
              this.successMessage = 'Availability updated successfully.';
            },
            error: () => {
              this.errorMessage = 'Error updating availability!';
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


}

