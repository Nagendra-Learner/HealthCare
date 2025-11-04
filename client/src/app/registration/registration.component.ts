import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../types/User';
import { HttpService } from '../../services/http.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
})
export class RegistrationComponent implements OnInit
{

  user!: User;
  itemForm!: FormGroup;
  successMessage: string|null = null; 
  errorMessage: string | null = null;

  constructor(private fb:FormBuilder, private httpService: HttpService,private router:Router)
  {}

  ngOnInit(): void 
  {
    this.itemForm = this.fb.group({
      username:['',[Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      email:['',[Validators.required, Validators.email]],
      password:['',[Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)]],
      role:['',Validators.required],
      specialty: [''],
      availability: ['']
    });

    this.itemForm.get('role')?.valueChanges.subscribe((roleValue) => {
      const specialtyControl = this.itemForm.get('specialty');
      const availabilityControl = this.itemForm.get('availability');

      if(roleValue == "DOCTOR")
      {
        specialtyControl?.setValidators([Validators.required]);
        availabilityControl?.setValidators([Validators.required]);
      }
      else
      {
        specialtyControl?.clearValidators();
        availabilityControl?.clearValidators();
      }

      specialtyControl?.updateValueAndValidity();
      availabilityControl?.updateValueAndValidity();
    });
  }

  onSubmit()
  {
    this.successMessage = null;
    this.errorMessage = null;
    
    if(this.itemForm.valid)
    { 
      console.log("itemForm.value = \n" + this.itemForm.value);

      let registerObservable;

      switch(this.itemForm.value.role)
      {
        case "PATIENT":
          registerObservable = this.httpService.registerPatient(this.itemForm.value);
          break;
        case "DOCTOR":
          registerObservable = this.httpService.registerDoctor(this.itemForm.value);
          break;
        case "RECEPTIONIST":
          registerObservable = this.httpService.registerReceptionist(this.itemForm.value);
          break;
        default:
          this.errorMessage = "Invalid Role Selected!";
          return;
      }

      registerObservable.subscribe({
        next: (registeredUser) => {
          this.user = registeredUser;
          this.successMessage = "Registration Successful.";
     
          console.log("User value = \n" + this.user);
          setTimeout(() => {this.successMessage = null; this.itemForm.reset(); this.router.navigate(['/login'])}, 2000);
        },
        error: (errorObj : HttpErrorResponse) => {
          this.successMessage = null;
          this.handleError(errorObj);
        } });
    }
    else
    {
      this.errorMessage = "Please fill out all required fields!";
    }
   
  }

  get f()
  {
    return this.itemForm.controls;
  }

  private handleError(error: HttpErrorResponse): void 
  {
    this.successMessage = null;
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
